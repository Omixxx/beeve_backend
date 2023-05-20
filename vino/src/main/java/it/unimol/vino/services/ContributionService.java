package it.unimol.vino.services;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.dto.mappers.ProviderDTOMapper;
import it.unimol.vino.exceptions.ContributionNotFoundException;

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
                contribution -> ContributionDTO.builder()
                        .id(contribution.getId())
                        .quantity(contribution.getQuantity())
                        .deliveryDate(contribution.getDate())
                        .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(contribution.getAssociatedGrapeType()))
                        .provider(providerDTOMapper.apply(contribution.getProvider()))
                        .build()
        ).collect(Collectors.toList());
    }


    public ContributionDTO get(Long id) {

        return this.contribution.findById(id)
                .map(specificContribution -> ContributionDTO.builder()
                        .id(specificContribution.getId())
                        .origin(specificContribution.getOrigin())
                        .country(specificContribution.getCountry())
                        .description(specificContribution.getDescription())
                        .quantity(specificContribution.getQuantity())
                        .deliveryDate(specificContribution.getDate())
                        .sugarDegree(specificContribution.getSugarDegree())
                        .photoURL(specificContribution.getPhotoURL())
                        .associatedGrapeType(GrapeTypeDTO.getFullGrapeTypeDTO(specificContribution.getAssociatedGrapeType()))
                        .provider(providerDTOMapper.apply(specificContribution.getProvider()))
                        .build())
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"));

    }

    public UserDTO getUser(Long contributionId) {
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

        Provider provider = this.provider.findById(request.getProviderId()).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + request.getProviderId() + " non è stato trovato")
        );

        GrapeType grapeType = this.grapeType.findById(request.getGrapeTypeId()).orElseThrow(
                () -> new GrapeTypeNotFoundException("Il tipo d'uva con ID " + request.getGrapeTypeId() + " non è stato trovato")
        );

        var contribution = Contribution.builder()
                .origin(request.getOrigin())
                .country(request.getCountry())
                .photoURL(request.getPhotoURL())
                .description(request.getDescription())
                .sugarDegree(request.getSugarDegree())
                .quantity(request.getQuantity())
                .date(request.getDate())
                .associatedGrapeType(grapeType)
                .provider(provider)
                .submitter(user)
                .build();

        contribution.setSubmitter(user);
        this.contribution.save(contribution);
        return "Il conferimento è stato registrato con l'id" + contribution.getId();
    }

}
