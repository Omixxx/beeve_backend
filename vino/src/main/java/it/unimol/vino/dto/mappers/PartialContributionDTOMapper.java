package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.models.entity.Contribution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class PartialContributionDTOMapper implements Function<Contribution, ContributionDTO> {
    private final ProviderDTOMapper providerDTOMapper;

    @Override
    public ContributionDTO apply(Contribution contribution) {

        return ContributionDTO.builder()
                .id(contribution.getId())
                .quantity(contribution.getQuantity())
                .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(contribution.getAssociatedGrapeType()))
                .provider(providerDTOMapper.apply(contribution.getProvider()))
                .build();
    }
}
