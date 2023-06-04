package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.exceptions.ImageNotLoadedException;
import it.unimol.vino.models.entity.Contribution;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
        String imagePath = contribution.getImage();
        return ContributionDTO.builder()
                .id(contribution.getId())
                .origin(contribution.getOrigin())
                .country(contribution.getCountry())
                .description(contribution.getDescription())
                .quantity(contribution.getQuantity())
                .sugarDegree(contribution.getSugarDegree())
                .image(imagePath != null ? loadImage(imagePath) : null)
                .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(contribution.getAssociatedGrapeType()))
                .provider(providerDTOMapper.apply(contribution.getProvider()))
                .build();
    }


    @NotNull
    private byte[] loadImage(String imagePath) {
        try {
            return Files.readAllBytes(new File(imagePath).toPath());
        } catch (IOException e) {
            throw new ImageNotLoadedException(
                    "Errore nel caricamento. " +
                            "DTT corrotto o mancante");
        }
    }
}
