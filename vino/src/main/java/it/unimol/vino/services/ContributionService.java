package it.unimol.vino.services;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.dto.ProviderDTO;
import it.unimol.vino.exceptions.ContributionNotFoundException;
import it.unimol.vino.exceptions.GrapeTypeNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.repository.GrapeTypeRepository;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.validation.Valid;
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
    private final ProviderRepository provider;
    private final GrapeTypeRepository grapeType;

    public List<ContributionDTO> getAll() {
        return this.contribution.findAll().stream().map(
                contribution -> ContributionDTO.builder()
                        .id(contribution.getId())
                        .quantity(contribution.getQuantity())
                        .associatedGrapeType(GrapeTypeDTO.getOnlyIDGrapeType(contribution.getAssociatedGrapeType()))
                        .provider(ProviderDTO.getName(contribution.getProvider()))
                        .build()
        ).collect(Collectors.toList());
    }


    public ContributionDTO get(Long id) {
        ContributionDTO contribution = this.contribution.findById(id)
                .map(specificContribution -> ContributionDTO.builder()
                        .id(specificContribution.getId())
                        .origin(specificContribution.getOrigin())
                        .country(specificContribution.getCountry())
                        .description(specificContribution.getDescription())
                        .quantity(specificContribution.getQuantity())
                        .sugarDegree(specificContribution.getSugarDegree())
                        .photoURL(specificContribution.getPhotoURL())
                        .associatedGrapeType(GrapeTypeDTO.getOnlyIDGrapeType(specificContribution.getAssociatedGrapeType()))
                        .provider(ProviderDTO.getNameNumberEmail(specificContribution.getProvider()))
                        .build())
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"));

        return contribution;

    }

    public List<Contribution> getByOrigin(String origin) {
        return this.contribution.findByOrigin(origin)
                .orElseThrow(() -> new ContributionNotFoundException("Non esiste alcun conferimento con origine " + origin));
    }

    public List<Contribution> getByCountry(String country) {
        return this.contribution.findByCountry(country)
                .orElseThrow(() -> new ContributionNotFoundException("Non esiste alcun conferimento con paese " + country));
    }

    public List<Contribution> getBySugarDegree(double sugarDegree) {
        return this.contribution.findBySugarDegree(sugarDegree)
                .orElseThrow(() -> new ContributionNotFoundException("Non esiste alcun conferimento con grado zuccherino " + sugarDegree));
    }

    public List<Contribution> getByQuantity(double quantity) {
        return this.contribution.findByQuantity(quantity)
                .orElseThrow(() -> new ContributionNotFoundException("Non esiste alcun conferimento con quantità " + quantity));
    }

    public List<Contribution> getByAssociatedGrapeType(GrapeType grapeType) {
        return this.contribution.findByAssociatedGrapeType(grapeType)
                .orElseThrow(() -> new ContributionNotFoundException("Non esiste alcun conferimento con tipo d'uva " + grapeType.getId()));
    }

    public String put(@Valid RegisterContributionRequest request) {
        Provider provider = this.provider.findById(request.getProviderId()).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + request.getProviderId() + " non è stato trovato")
        );

        GrapeType grapeType = this.grapeType.findById(request.getGrapeTypeId()).orElseThrow(
                () -> new GrapeTypeNotFoundException("Il tipo d'uva con ID " + request.getGrapeTypeId() + " non è stato trovato")
        );

        var contribution = Contribution.builder()
                .origin(request.getCountry())
                .country(request.getCountry())
                .photoURL(request.getPhotoURL())
                .description(request.getDescription())
                .sugarDegree(request.getSugarDegree())
                .quantity(request.getQuantity())
                .date(request.getDate())
                .associatedGrapeType(grapeType)
                .provider(provider)
                .build();

        this.contribution.save(contribution);
        return "Il conferimento è stato registrato con l'id" + contribution.getId();
    }

    public Contribution replace(Long id, @Valid Contribution contribution) {

        if (!this.contribution.existsById(id)) {
            throw new ContributionNotFoundException("Il conferimento con id " + id + " non esiste");
        }

        contribution.setId(id);

        return this.contribution.save(contribution);
    }

    public void updateOrigin(Long id, String origin) {
        this.contribution.findById(id)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"))
                .setOrigin(origin);
    }

    public void updateCountry(Long id, String country) {
        this.contribution.findById(id)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"))
                .setCountry(country);
    }

    public void updatePhoto(Long id, String URL) {
        this.contribution.findById(id)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"))
                .setPhotoURL(URL);
    }

    public void updateDescription(Long id, String description) {
        this.contribution.findById(id)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"))
                .setDescription(description);
    }

    public void updateSugarDegree(Long id, double sugarDegree) {
        this.contribution.findById(id)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"))
                .setSugarDegree(sugarDegree);
    }

    public void updateQuantity(Long id, double quantity) {
        this.contribution.findById(id)
                .orElseThrow(() -> new ContributionNotFoundException("Il conferimento con id " + id + " non esiste"))
                .setQuantity(quantity);
    }

    public void delete() {
        this.contribution.deleteAll();
    }

    public void delete(Long id) {
        this.contribution.deleteById(id);
    }

    public void deleteAllByOrigin(String origin) {
        this.contribution.deleteAllByOrigin(origin);
    }

    public void deleteAllByCountry(String country) {
        this.contribution.deleteAllByCountry(country);
    }

    public void deleteAllBySugarDegree(double sugarDegree) {
        this.contribution.deleteAllBySugarDegree(sugarDegree);
    }

    public void deleteAllByAssociatedGrapeType(GrapeType grapeType) {
        this.contribution.deleteAllByAssociatedGrapeType(grapeType);
    }
}
