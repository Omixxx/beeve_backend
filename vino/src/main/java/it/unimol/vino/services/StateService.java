package it.unimol.vino.services;

import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository){
        this.stateRepository = stateRepository;
    }

    public String newState(NewStateRequest newStateRequest){
        var state = State.builder()
                .name(newStateRequest.getName())
                .processes(new ArrayList<>())
                .build();
        if(newStateRequest.getDescription()!=null) state.setDescription(newStateRequest.getDescription());
        return this.stateRepository.save(state).getId().toString();
    }
}
