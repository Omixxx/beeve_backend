package it.unimol.vino.services;

import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.exceptions.StateAlreadyExist;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State newState(NewStateRequest request) {
        State state = State.builder()
                .name(request.getStateName())
                .doesProduceWaste(request.getDoesProduceWaste())
                .processes(new ArrayList<>())
                .build();

        this.stateRepository.findByName(state.getName()).ifPresent(
                (s) -> {
                    throw new StateAlreadyExist("Stato con nome " + state.getName() + " già esistente");
                }
        );

        return this.stateRepository.save(state);
    }

    public List<StateDTO> getAllStates() {
        List<StateDTO> states = new ArrayList<>();
        this.stateRepository.findAll().forEach(
                (state) -> {
                    states.add(StateDTO.builder()
                            .id(state.getId())
                            .name(state.getName())
                            .doesProduceWaste(state.getDoesProduceWaste())
                            .build());
                }
        );
        return states;
    }
}
