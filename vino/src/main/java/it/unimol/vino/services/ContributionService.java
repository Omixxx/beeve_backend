package it.unimol.vino.services;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.dto.mappers.FullContributionDTOMapper;
import it.unimol.vino.dto.mappers.PartialContributionDTOMapper;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.repository.GrapeTypeRepository;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Validated
@AllArgsConstructor
public class ContributionService {

    private final ContributionRepository contribution;
    private final UserRepository userRepository;
    private final ProviderRepository provider;
    private final GrapeTypeRepository grapeType;
    private final PartialContributionDTOMapper partialContributionDTOMapper;
    private final FullContributionDTOMapper fullContributionDTOMapper;


    public List<ContributionDTO> getAll() {
        return this.contribution.findAll()
                .stream()
                .map(partialContributionDTOMapper)
                .toList();
    }


    public ContributionDTO get(Long id) {
        return this.contribution.findById(id)
                .map(fullContributionDTOMapper)
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


        var contribution = Contribution.builder()
                .origin(request.getOrigin())
                .country(request.getCountry())
                .description(request.getDescription())
                .sugarDegree(Double.parseDouble(request.getSugarDegree()))
                .quantity(Double.parseDouble(request.getQuantity()))
                .date(request.getDate())
                .associatedGrapeType(grapeType)
                .provider(provider)
                .submitter(user)
                .build();

        contribution.setSubmitter(user);

        if (Objects.nonNull(request.getImage())) {
            String IMAGE_FOLDER = "/src/main/resources/static/images/";
            StringBuilder path = new StringBuilder(System.getProperty("user.dir")).append(IMAGE_FOLDER).append(request.getImage().getOriginalFilename());

            try {
                contribution.setImage(path.toString());
                request.getImage().transferTo(new File(path.toString()));
            } catch (IOException e) {
                throw new ImageNotLoadedException("Errore nel caricamento dell'immagine");
            }
        }
        this.contribution.save(contribution);
        return "Il conferimento è stato registrato con l'id " + contribution.getId();
    }
}
