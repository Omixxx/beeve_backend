package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.models.entity.State;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StateDTOMapper implements Function<State, StateDTO> {
    @Override
    public StateDTO apply(State state) {
        return StateDTO.builder()
                .id(state.getId())
                .name(state.getName())
                .build();
    }
}
