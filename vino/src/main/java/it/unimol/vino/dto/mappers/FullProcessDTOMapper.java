package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.models.entity.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class FullProcessDTOMapper implements Function<Process, ProcessDTO> {
    private final ProcessFullCurrentStateDTOMapper processFullCurrentStateDTOMapper;
    private final ProcessPartialContributionDTOMapper processPartialContributionDTOMapper;
    private final ItemProcessUseItemDTOMapper itemProcessUseItemDTOMapper;

    @Override
    public ProcessDTO apply(Process process) {
        return ProcessDTO.builder()
                .currentState(processFullCurrentStateDTOMapper.apply(process))
                .contributions(process.getContribution().stream()
                        .map(processPartialContributionDTOMapper).toList())

                .items(process.getItem().stream().map(itemProcessUseItemDTOMapper)
                        .toList())
                .currentWaste(process.getCurrentWaste())
                .build();
    }
}
