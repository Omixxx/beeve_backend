package it.unimol.vino.services;

import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.*;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.Sorter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public Long createNewProcess(NewProcessRequest request) {
        HashMap<State, Integer> stateSequenceMap = new HashMap<>();
        request.getStateIdSequence().forEach((stateId, sequence) -> {
            State state = this.stateRepository.findById(stateId).orElseThrow(
                    () -> new StateNotFoundException("Stato con id " + stateId + " non trovato")
            );
            stateSequenceMap.put(state, sequence);
        });
        Sorter.sortMapByValue(stateSequenceMap);

        Process process = new Process(stateSequenceMap);
        User user = this.getUser();
        process.setCreator(user);
        return this.processRepository.save(process).getId();
    }


    public void addState(AddStateToProcessRequest request) {
        Process process = this.getProcess(request.getProcessId());

        State state = this.stateRepository.findById(request.getStateId()).orElseThrow(
                () -> new StateNotFoundException("Stato non trovato")
        );

        process.addState(state, request.getSequence());
    }

    public void startProcess(Long processId) {
        Process process = this.getProcess(processId);

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
        process.getUserProgressProcessList().add(UserProgressesProcess.builder()
                .user(user)
                .process(process)
                .description(description)
                .build());

        process.getCurrentState().setEndDate(new Date());
        ProcessHasStates nextState = process.getNextState();
        nextState.setStartDate(new Date());
        process.setCurrentState(nextState);
        return nextState.getState().getName();
    }

    public void cancelProcess(Long processId, String description) {
        Process process = this.getProcess(processId);

        this.ensureProcessIsNotCancelled(process);
        this.ensureProcessHasStates(process);
        this.ensureProcessIsStarted(process);

        User user = this.getUser();

        process.getCurrentState().setEndDate(new Date());
        process.setCurrentState(null);
        process.setCanceller(user);
        process.setCancellationDate(new Date());
        process.setCancellationDescription(description);
    }


    private Process getProcess(Long processId) {
        return this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );
    }

    private void ensureProcessIsStarted(Process process) {
        if (Objects.isNull(process.getCurrentState()))
            throw new ProcessAlreadyStarted("Il processo non è stato avviato");
    }

    private void ensureProcessHasStates(Process process) {
        if (process.getStatesOrderedBySequence().isEmpty())
            throw new ProcessHasNoStatesException("Il processo non ha stati");
    }

    private void ensureProcessIsNotCancelled(Process process) {
        if (Objects.isNull(process.getCurrentState()) && Objects.nonNull(process.getCanceller()))
            throw new ProcessCancelledException("Il processo è già stato cancellato");
    }

    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Utente non trovato")
        );
    }

}
