package it.unimol.vino.services;

import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.*;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.repository.UserProgressProcessRepository;
import it.unimol.vino.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    public Long createNewProcess(@NotNull NewProcessRequest request) {
        request.getStates().forEach(System.out::println);

        List<State> alreadyOrderedStateList = new ArrayList<>();
        request.getStates().forEach((stateId) -> {
            State state = this.stateRepository.findById(stateId).orElseThrow(
                    () -> new StateNotFoundException("Stato con id " + stateId + " non trovato")
            );
            alreadyOrderedStateList.add(state);
        });

        Process process = new Process(alreadyOrderedStateList);
        User user = this.getUser();
        process.setCreator(user);
        return this.processRepository.save(process).getId();
    }


    public void addState(@NotNull AddStateToProcessRequest request) {
        Process process = this.getProcess(request.getProcessId());

        State state = this.stateRepository.findById(request.getStateId()).orElseThrow(
                () -> new StateNotFoundException("Stato non trovato")
        );

        process.addState(state, request.getSequence());
    }

    public void startProcess(Long processId) {
        Process process = this.getProcess(processId);

        if (Objects.nonNull(process.getCurrentState())) {
            throw new ProcessAlreadyStartedException("Il processo è già stato avviato");
        }

        this.ensureProcessIsNotCancelled(process);
        this.ensureProcessHasStates(process);

        ProcessHasStates initialState = process.getStatesOrderedBySequence().get(0);
        initialState.setStartDate(new Date());
        process.setCurrentState(initialState);
        this.processRepository.save(process);
    }


    @Transactional
    public String progressState(Long processId, String description) {
        Process process = this.getProcess(processId);

        this.ensureProcessHasStates(process);
        this.ensureProcessIsNotCancelled(process);
        this.ensureProcessIsStarted(process);

        User user = this.getUser();
        UserProgressesProcess userProgressesProcess = UserProgressesProcess.builder()
                .user(user)
                .process(process)
                .description(description)
                .build();

        process.getUserProgressProcessList().add(userProgressesProcess);
        user.getProgressedProcesses().add(userProgressesProcess);

        process.getCurrentState().setEndDate(new Date());
        ProcessHasStates nextState = process.getNextState();
        nextState.setStartDate(new Date());
        process.setCurrentState(nextState);

        this.userProgressProcessRepository.save(userProgressesProcess);
        return nextState.getState().getName();
    }

    public void cancelProcess(Long processId, String description) {
        Process process = this.getProcess(processId);

        this.ensureProcessHasStates(process);
        this.ensureProcessIsNotCancelled(process);
        this.ensureProcessIsStarted(process);

        User user = this.getUser();

        process.getCurrentState().setEndDate(new Date());
        process.setCurrentState(null);
        process.setCanceller(user);
        process.setCancellationDate(new Date());
        process.setCancellationDescription(description);
        processRepository.save(process);
    }

    public List<ProcessDTO> getAllProcesses() {
        List<ProcessDTO> processDTOList = new ArrayList<>();
        this.processRepository.findAll().forEach(process -> {
            processDTOList.add(ProcessDTO.getFullProcessDTO(process));
        });
        return processDTOList;
    }

    private Process getProcess(Long processId) {
        return this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );
    }

    private void ensureProcessIsStarted(@NotNull Process process) {
        if (Objects.isNull(process.getCurrentState()))
            throw new ProcessAlreadyStartedException("Il processo non è stato avviato");
    }

    private void ensureProcessHasStates(@NotNull Process process) {
        if (process.getStatesOrderedBySequence().isEmpty())
            throw new ProcessHasNoStatesException("Il processo non ha stati");
    }

    private void ensureProcessIsNotCancelled(@NotNull Process process) {
        if (Objects.isNull(process.getCurrentState()) && Objects.nonNull(process.getCanceller()))
            throw new ProcessCancelledException("Il processo risulta cancellato");
    }

    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Utente non trovato")
        );
    }
}
