package it.unimol.vino.services;

import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public Long newState(NewStateRequest request) {
        State state = State.builder()
                .name(request.getStateName())
                .doesProduceWaste(request.getDoesProduceWaste())
                .processes(new ArrayList<>())
                .build();
        return this.stateRepository.save(state).getId();
    }
}
