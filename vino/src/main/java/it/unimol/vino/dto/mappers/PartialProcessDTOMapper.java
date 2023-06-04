package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.models.entity.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class PartialProcessDTOMapper implements Function<Process, ProcessDTO> {
    private final ProcessPartialCurrentStateDTOMapper processPartialCurrentStateDTOMapper;

    @Override
    public ProcessDTO apply(Process process) {
        return ProcessDTO.builder()
                .id(process.getId())
                .currentState(processPartialCurrentStateDTOMapper.apply(process))
                .build();
    }
}
