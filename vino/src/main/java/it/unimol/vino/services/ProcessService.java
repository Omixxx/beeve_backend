package it.unimol.vino.services;

import it.unimol.vino.models.entity.ProcessHasStates;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.SetStateRequest;
import it.unimol.vino.models.request.SetWasteRequest;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import it.unimol.vino.models.entity.Process;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProcessService {
    private final ProcessRepository processRepository;
    private final StateRepository stateRepository;



    public String newProductionProcess(NewProcessRequest newProcessRequest) {
        var process = Process.builder()
                .startDate(newProcessRequest.getDate())
                .states(new ArrayList<>())
                .build();
        if (newProcessRequest.getName() != null) process.setName(newProcessRequest.getName());
        return this.processRepository.save(process).getId().toString();
    }

    public String setState(SetStateRequest setStateRequest) {
        var process = this.processRepository.findById(setStateRequest.getProcessId());
        var state = this.stateRepository.findById(setStateRequest.getStateId());
        if (process.isPresent() && state.isPresent()) {
            process.get().setState(state.get());
            return "ok";
        }
        return "error";
    }

    public String setWaste(SetWasteRequest setWasteRequest){
        var process = this.processRepository.findById(setWasteRequest.getProcessId());
        var state = process.get().getState();
        if(process.isPresent() && state != null){
            process.get().setWaste(setWasteRequest.getWaste());
            return "ok";
        }
        return "error";
    }

    public String addState(AddStateToProcessRequest addStateToProcessRequest) throws Exception {
        Process process = this.processRepository.findById(addStateToProcessRequest.getProcessId()).orElseThrow(()->new Exception());
        State state = this.stateRepository.findById(addStateToProcessRequest.getStateId()).orElseThrow(()->new Exception());
        process.addState(state, addStateToProcessRequest.getSequence());
        this.processRepository.save(process);
        return "ok";
    }
}
