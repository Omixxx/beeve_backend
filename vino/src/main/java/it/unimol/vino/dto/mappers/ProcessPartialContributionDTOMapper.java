package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.models.entity.ProcessUseContribution;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProcessPartialContributionDTOMapper implements Function<ProcessUseContribution, ContributionDTO> {

    @Override
    public ContributionDTO apply(ProcessUseContribution processUseContribution) {
        return ContributionDTO.builder()
                .id(processUseContribution.getContribution().getId())
                .quantity(processUseContribution.getQuantity())
                .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(processUseContribution
                        .getContribution().getAssociatedGrapeType()))
                .build();
    }
}
