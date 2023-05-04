package it.unimol.vino.services;

import it.unimol.vino.exceptions.ProcessNotFoundException;
import it.unimol.vino.exceptions.StateNotFoundException;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.utils.Sorter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final StateRepository stateRepository;

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
        return this.processRepository.save(process).getId();
    }

    public String progressState(Long processId) {
        Process process = this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );

        process.setNextState();
        return process.getCurrentState().getState().getName();
    }

    public void addState(AddStateToProcessRequest request) {
        Process process = this.processRepository.findById(request.getProcessId()).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );

        State state = this.stateRepository.findById(request.getStateId()).orElseThrow(
                () -> new StateNotFoundException("Stato non trovato")
        );

        process.addState(state, request.getSequence());
    }

}
