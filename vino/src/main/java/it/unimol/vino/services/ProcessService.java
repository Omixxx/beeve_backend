package it.unimol.vino.services;


import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.*;
import it.unimol.vino.models.request.AddStateToProcessRequest;
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

    @Transactional
    public Long createNewProcess(NewProcessRequest request) {
        List<State> alreadyOrderedStateList = new ArrayList<>();
        if (DuplicatesChecker.hasDuplicates(request.getStates()))
            throw new DuplicateStateException("Stati duplicati non ammessi");

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


    public void addState(@NotNull AddStateToProcessRequest request) {
        Process process = this.getProcessFromDb(request.getProcessId());

        State state = this.stateRepository.findById(request.getStateId()).orElseThrow(
                () -> new StateNotFoundException("Stato non trovato")
        );

        process.addState(state, request.getSequence());
    }

    @Transactional
    public String progressState(Long processId, ProgressProcessRequest request) {
        Process process = this.getProcessFromDb(processId);

        this.ensureProcessHasStates(process);
        this.ensureProcessIsNotCompleted(process);
        this.ensureProcessIsNotAborted(process);

        User user = this.getUser();
        UserProgressesProcess userProgressesProcess = UserProgressesProcess.builder()
                .user(user)
                .process(process)
                .completedState(process.getCurrentState().getState())
                .waste(request.getWaste())
                .date(new Date())
                .description(request.getDescription())
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
            process.setCurrentState(null);
            return "Processo terminato con successo";
        }

        nextState.get().setStartDate(new Date());
        process.setCurrentState(nextState.get());

        return "Processo avanzato con successo verso lo stato "
                + nextState.get().getState().getName();
    }

    public void abortProcess(Long processId, String description) {
        Process process = this.getProcessFromDb(processId);

        this.ensureProcessHasStates(process);
        this.ensureProcessIsNotAborted(process);

        User user = this.getUser();

        process.getCurrentState().setEndDate(new Date());
        process.setCurrentState(null);
        process.setUserWhoAborted(user);
        process.setAbortionDate(new Date());
        process.setAbortionDescription(description);
        processRepository.save(process);
    }

    public List<ProcessDTO> getAllProcesses() {
        return this.processRepository.findAll().stream()
                .filter(process -> Objects.nonNull(process.getCurrentState()))
                .map(process -> ProcessDTO.builder()
                        .id(process.getId())
                        .currentState(StateDTO.builder()
                                .name(process.getCurrentState().getState().getName())
                                .build())
                        .build()
                ).toList();
    }

    public ProcessDTO getProcess(Long processId) {
        Process process = this.getProcessFromDb(processId);
        return ProcessDTO.builder()
                .currentState(StateDTO.builder()
                        .id(process.getCurrentState().getState().getId())
                        .name(process.getCurrentState().getState().getName())
                        .build())
                .contributions(process.getContribution().stream().map(processUseContribution -> ContributionDTO.builder()
                        .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(processUseContribution.getContribution().getAssociatedGrapeType()))
                        .quantity(processUseContribution.getQuantity())
                        .build()).toList())
                .currentWaste(process.getCurrentWaste())
                .stalkWaste(process.getStalkWaste())
                .build();
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
        if (Objects.isNull(process.getCurrentState()) && Objects.nonNull(process.getUserWhoAborted()))
            throw new ProcessAbortedException("Il processo risulta interrotto");
    }

    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Utente non trovato")
        );
    }

    private void ensureProcessIsNotCompleted(@NonNull Process process) {
        if (Objects.isNull(process.getCurrentState()))
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

        this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );

        UserProgressesProcess completedProcess = this.getUser().getProgressedProcesses().stream()
                .filter(userProgressesProcess -> userProgressesProcess.getProcess().getId().equals(processId))
                .filter(userProgressesProcess -> userProgressesProcess.getCompletedState().getId().equals(stateId))
                .findFirst().orElseThrow(
                        () -> new ProcessDidNotProgressException("il processo " + processId + " non ha mai completato lo stato " + stateId)
                );

        return CompletedStateResponse.builder()
                .waste(completedProcess.getWaste())
                .description(completedProcess.getDescription())
                .build();
    }
}
