package it.unimol.vino.services;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.dto.mappers.ProviderDTOMapper;
import it.unimol.vino.exceptions.ContributionNotFoundException;
import it.unimol.vino.exceptions.ImageNotLoadedException;

import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import it.unimol.vino.exceptions.GrapeTypeNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.repository.GrapeTypeRepository;
import it.unimol.vino.repository.ProviderRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor
public class ContributionService {

    private final ContributionRepository contribution;
    private final UserRepository userRepository;
    private final ProviderRepository provider;
    private final GrapeTypeRepository grapeType;

    private final ProviderDTOMapper providerDTOMapper;

    public List<ContributionDTO> getAll() {
        return this.contribution.findAll().stream().map(
                contribution -> {
                    try {
                        return ContributionDTO.builder()
                                .id(contribution.getId())
                                .quantity(contribution.getQuantity())
                                .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(contribution.getAssociatedGrapeType()))
                                .provider(providerDTOMapper.apply(contribution.getProvider()))
                                .image(Files.readAllBytes(new File(contribution.getImage()).toPath()))
                                .build();
                    } catch (IOException e) {
                        throw new ImageNotLoadedException(e.getMessage());
                    }
                }
        ).collect(Collectors.toList());
    }


    public ContributionDTO get(Long id) {
        return this.contribution.findById(id)
                .map(specificContribution -> {
                    try {
                        return ContributionDTO.builder()
                                .id(specificContribution.getId())
                                .origin(specificContribution.getOrigin())
                                .country(specificContribution.getCountry())
                                .description(specificContribution.getDescription())
                                .quantity(specificContribution.getQuantity())
                                .sugarDegree(specificContribution.getSugarDegree())
                                .image(Files.readAllBytes(new File(specificContribution.getImage()).toPath()))
                                .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(specificContribution.getAssociatedGrapeType()))
                                .provider(providerDTOMapper.apply(specificContribution.getProvider()))
                                .build();
                    } catch (IOException e) {
                        throw new ImageNotLoadedException("Errore nel caricamento dell'immagine\n" + e.getMessage());
                    }
                })
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"));
    }

    public UserDTO getSubmitter(Long contributionId) {
        User submitterId = this.contribution.findById(contributionId)
                .map(Contribution::getSubmitter)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + contributionId + " non esiste"));
        return UserDTO.builder()
                .id(submitterId.getId())
                .firstName(submitterId.getFirstName())
                .lastName(submitterId.getLastName())
                .build();
    }

    public String put(@Valid RegisterContributionRequest request) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("L'utente con email " + userEmail + " non esiste")
        );

        Provider provider = this.provider.findById(Long.parseLong(request.getProviderId())).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + request.getProviderId() + " non è stato trovato")
        );

        GrapeType grapeType = this.grapeType.findById(Long.parseLong(request.getGrapeTypeId())).orElseThrow(
                () -> new GrapeTypeNotFoundException("Il tipo d'uva con ID " + request.getGrapeTypeId() + " non è stato trovato")
        );

        String IMAGE_FOLDER = "/src/main/resources/static/images/";
        StringBuilder path = new StringBuilder(System.getProperty("user.dir")).append(IMAGE_FOLDER).append(request.getImage().getOriginalFilename());

        var contribution = Contribution.builder()
                .origin(request.getOrigin())
                .country(request.getCountry())
                .image(path.toString())
                .description(request.getDescription())
                .sugarDegree(Double.parseDouble(request.getSugarDegree()))
                .quantity(Double.parseDouble(request.getQuantity()))
                .date(request.getDate())
                .associatedGrapeType(grapeType)
                .provider(provider)
                .submitter(user)
                .build();

        contribution.setSubmitter(user);
        try {
            request.getImage()
                    .transferTo(new File(path.toString()));
        } catch (IOException e) {
            throw new ImageNotLoadedException("Errore nel caricamento dell'immagine");
        }
        this.contribution.save(contribution);
        return "Il conferimento è stato registrato con l'id " + contribution.getId();
    }

}
