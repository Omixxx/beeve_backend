package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.CurrentStateDTO;
import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.models.entity.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ProcessFullCurrentStateDTOMapper implements Function<Process, CurrentStateDTO> {
    private final StateDTOMapper stateDTOMapper;

    @Override
    public CurrentStateDTO apply(Process process) {
        return CurrentStateDTO.builder()
                .user(UserDTO.builder().firstName(process.getUserWhoProgressedToTheCurrentState().getFirstName()).build())
                .state(stateDTOMapper.apply(process.getCurrentState().getState()))
                .build();
    }
}
