package it.unimol.vino.services;


import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.dto.mappers.*;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.*;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.ProgressProcessRequest;
import it.unimol.vino.models.response.CompletedStateResponse;
import it.unimol.vino.repository.*;
import it.unimol.vino.utils.DuplicatesChecker;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final UserProgressProcessRepository userProgressProcessRepository;
    private final ItemRepository itemRepository;
    private final ContributionRepository contributionRepository;
    private final PartialProcessDTOMapper partialProcessDTOMapper;
    private final FullProcessDTOMapper fullProcessDTOMapper;


    @Transactional
    public Long createNewProcess(NewProcessRequest request) {
        List<State> alreadyOrderedStateList = new ArrayList<>();

        State finalState = this.stateRepository.findByName("Completato").orElseThrow(
                () -> new InternalServerErrorException("Errore Interno, contattare l'amministratore")
        );

        if (request.getItemIdUsedQuantity().values().stream().mapToInt(Integer::intValue).sum() == 0)
            throw new InvalidItemQuantityException("La quantità di item deve essere maggiore di 0");

        if (request.getContributionIdQuantity().values().stream().mapToDouble(Double::doubleValue).sum() == 0)
            throw new InvalidContributionQuantityException("La quantità di conferimento utilizzata deve essere maggiore di 0");

        if (DuplicatesChecker.hasDuplicates(request.getStates()))
            throw new DuplicateStateException("Stati duplicati non ammessi");

        if (request.getStates().isEmpty())
            throw new ProcessHasNoStatesException("Il processo deve avere almeno uno stato");

        if (request.getStates().size() == 1 && request.getStates().get(0).equals(finalState.getId()))
            throw new ProcessHasNoStatesException("Il processo non puo avere solo lo stato finale");

        if (request.getStates().contains(finalState.getId()) &&
                !Objects.equals(request.getStates().get(request.getStates().size() - 1), finalState.getId()))
            throw new ProcessHasNoStatesException("Il processo non puo avere lo stato finale" +
                    " in posizioni diverse dall'ultima");

        if (!request.getStates().contains(finalState.getId()))
            request.getStates().add(finalState.getId());

        request.getStates().forEach((stateId) -> {
            State state = this.stateRepository.findById(stateId).orElseThrow(
                    () -> new StateNotFoundException("Stato con id " + stateId + " non trovato")
            );

            alreadyOrderedStateList.add(state);
        });

        HashMap<Item, Integer> itemQuantityMap = new HashMap<>();
        request.getItemIdUsedQuantity().forEach((itemId, quantity) -> {
            Item item = this.itemRepository.findById(itemId).orElseThrow(
                    () -> new ItemNotFoundException("Item con id " + itemId + " non trovato")
            );
            Integer totalQuantity = item.getTotQuantity();
            if (totalQuantity < quantity)
                throw new QuantityNotAvailableException("Quantità non sufficiente per l'item " + item.getDescription() +
                        " richiesta: " + quantity + " disponibile: " + totalQuantity);
            item.setTotQuantity(totalQuantity - quantity);
            itemQuantityMap.put(item, quantity);
        });

        HashMap<Contribution, Double> contributionQuantityMap = new HashMap<>();
        request.getContributionIdQuantity().forEach((contributionId, quantity) -> {
            Contribution contribution = this.contributionRepository.findById(contributionId).orElseThrow(
                    () -> new ContributionNotFoundException("Conferimento con id " + contributionId + " non trovato")
            );
            Double totalQuantity = contribution.getQuantity();
            if (totalQuantity < quantity)
                throw new QuantityNotAvailableException("Quantità non sufficiente per il conferimento "
                        + contribution.getId() + " richiesta: " + quantity + " disponibile: " + totalQuantity);
            contribution.setQuantity(totalQuantity - quantity);
            contributionQuantityMap.put(contribution, quantity);
        });

        Process process = new Process(alreadyOrderedStateList, itemQuantityMap, contributionQuantityMap);
        User user = this.getUser();
        process.setCreator(user);

        return this.processRepository.save(process).getId();
    }

    @Transactional
    public String progressState(Long processId, ProgressProcessRequest request) {
        Process process = this.getProcessFromDb(processId);

        this.ensureProcessIsNotCompleted(process);
        this.ensureProcessIsNotAborted(process);

        User user = this.getUser();
        UserProgressesProcess userProgressesProcess = UserProgressesProcess.builder()
                .user(user).process(process)
                .completedState(process.getCurrentState().getState())
                .waste(request.getWaste())
                .date(new Date())
                .description(Objects.nonNull(request.getDescription())
                        ? request.getDescription() : "")
                .build();

        process.getUserProgressProcessList().add(userProgressesProcess);
        user.getProgressedProcesses().add(userProgressesProcess);
        this.userProgressProcessRepository.save(userProgressesProcess);

        if (!process.getCurrentState().getState().getDoesProduceWaste() && request.getWaste() > 0)
            throw new WasteNotAllowedException("Lo stato " + process.getCurrentState().getState().getName() +
                    " non produce rifiuti");


        process.setCurrentWaste(request.getWaste() + process.getCurrentWaste());
        process.getCurrentState().setEndDate(new Date());
        Optional<ProcessHasStates> nextState = process.getNextState();

        if (nextState.isEmpty()) {
            return "Processo terminato con successo";
        }

        nextState.get().setStartDate(new Date());
        process.setCurrentState(nextState.get());

        return "Processo avanzato con successo verso lo stato "
                + nextState.get().getState().getName();
    }

    public void abortProcess(Long processId, String description) {

        Process process = this.getProcessFromDb(processId);

        this.ensureProcessIsNotCompleted(process);
        this.ensureProcessHasStates(process);
        this.ensureProcessIsNotAborted(process);

        User user = this.getUser();

        process.getCurrentState().setEndDate(new Date());
        process.setUserWhoAborted(user);
        process.setAbortionDate(new Date());
        process.setAbortionDescription(description);
        processRepository.save(process);
    }

    public List<ProcessDTO> getAllProcesses() {
        return this.processRepository.findByCurrentStateNotNull().stream()
                .map(partialProcessDTOMapper)
                .toList();
    }

    public ProcessDTO getProcess(Long processId) {
        Process process = this.getProcessFromDb(processId);
        return this.fullProcessDTOMapper.apply(process);
    }

    private Process getProcessFromDb(Long processId) {
        return this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );
    }

    private void ensureProcessHasStates(@NotNull Process process) {
        if (process.getStatesOrderedBySequence().isEmpty())
            throw new ProcessHasNoStatesException("Il processo non ha stati");
    }

    private void ensureProcessIsNotAborted(@NotNull Process process) {
        if (Objects.nonNull(process.getUserWhoAborted()) && Objects.nonNull(process.getAbortionDate()))
            throw new ProcessAbortedException("Il processo risulta interrotto");
    }

    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Utente non trovato")
        );
    }

    private void ensureProcessIsNotCompleted(@NonNull Process process) {
        State finalState = this.stateRepository.findByName("Completato").orElseThrow(
                () -> new InternalServerErrorException("Errore interno, contattare l'amministratore")
        );
        if (process.getCurrentState().getState().getId().equals(finalState.getId()))
            throw new ProcessIsCompletedException("Il processo risulta già completato");
    }

    public List<StateDTO> getProcessStates(Long processId) {
        return this.getProcessFromDb(processId).getStates().stream().map(processHasStates -> StateDTO.builder()
                .id(processHasStates.getState().getId())
                .name(processHasStates.getState().getName())
                .doesProduceWaste(processHasStates.getState().getDoesProduceWaste())
                .build()).toList();
    }

    public CompletedStateResponse getCompletedState(Long processId, Long stateId) {

        Process process = this.getProcessFromDb(processId);
        State state = this.stateRepository.findById(stateId).orElseThrow(
                () -> new StateNotFoundException("Stato non trovato")
        );

        List<UserProgressesProcess> userProgressesProcesses =
                this.userProgressProcessRepository.findByProcessAndCompletedState(process, state);

        if (userProgressesProcesses.isEmpty())
            throw new ProcessDidNotProgressException("il processo "
                    + processId + " non ha mai completato lo stato " + state.getName());

        if (userProgressesProcesses.size() > 1)
            throw new InternalServerErrorException("Errore interno, contattare l'amministratore");
        UserProgressesProcess completedProcess = userProgressesProcesses.get(0);

        return CompletedStateResponse.builder()
                .waste(completedProcess.getWaste())
                .description(completedProcess.getDescription())
                .build();
    }

    public Double getGrapeUsedInProcess(Long processId) {
        return this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        ).getContribution().stream().mapToDouble(ProcessUseContribution::getQuantity).sum();
    }
}
