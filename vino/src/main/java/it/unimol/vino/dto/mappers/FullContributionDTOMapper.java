package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.exceptions.ImageNotLoadedException;
import it.unimol.vino.models.entity.Contribution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class FullContributionDTOMapper implements Function<Contribution, ContributionDTO> {

    private final ProviderDTOMapper providerDTOMapper;

    @Override
    public ContributionDTO apply(Contribution contribution) {
        try {
            return ContributionDTO.builder()
                    .id(contribution.getId())
                    .origin(contribution.getOrigin())
                    .country(contribution.getCountry())
                    .description(contribution.getDescription())
                    .quantity(contribution.getQuantity())
                    .sugarDegree(contribution.getSugarDegree())
                    .image(contribution.getImage() != null ?
                            Files.readAllBytes(new File(contribution.getImage()).toPath())
                            : null
                    )
                    .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(contribution.getAssociatedGrapeType()))
                    .provider(providerDTOMapper.apply(contribution.getProvider()))
                    .build();
        } catch (IOException e) {
            throw new ImageNotLoadedException("Errore nel caricamento dell'immagine\n" + e.getMessage());
        }
    }
}
