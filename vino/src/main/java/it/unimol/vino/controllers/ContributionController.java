package it.unimol.vino.controllers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.services.ContributionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @GetMapping
    public List<ContributionDTO> getAll() {
        return this.contributionService.getAll();
    }

    @DeleteMapping
    public void delete() {
        this.contributionService.delete();
    }

    @PostMapping
    public String put(@RequestBody RegisterContributionRequest request) {
        return this.contributionService.put(request);
    }

    @GetMapping("/{id}")
    public ContributionDTO get(@PathVariable Long id) {
        return this.contributionService.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.contributionService.delete(id);
    }

    @DeleteMapping(params = "origin")
    public void deleteAllByOrigin(@Valid @RequestParam String origin) {
        this.contributionService.deleteAllByOrigin(origin);
    }

    @DeleteMapping(params = "country")
    public void deleteAllByCountry(@Valid @RequestParam String country) {
        this.contributionService.deleteAllByCountry(country);
    }

    @DeleteMapping(params = "sugarDegree")
    public void deleteAllBySugarDegree(@Valid @RequestParam double sugarDegree) {
        this.contributionService.deleteAllBySugarDegree(sugarDegree);
    }

    @DeleteMapping(params = "associatedGrapeType")
    public void deleteAllByAssociatedGrapeType(@Valid @RequestParam GrapeType associatedGrapeType) {
        this.contributionService.deleteAllByAssociatedGrapeType(associatedGrapeType);
    }

    @PutMapping("/{id}/replace")
    public Contribution replace(@PathVariable Long id, @RequestBody Contribution contribution) {
        return this.contributionService.replace(id, contribution);
    }

    @PatchMapping("/{id}/origin")
    public void updateOrigin(@PathVariable Long id, @Valid @RequestParam String origin) {
        this.contributionService.updateOrigin(id, origin);
    }

    @PatchMapping("/{id}/country")
    public void updateCountry(@PathVariable Long id, @Valid @RequestParam String country) {
        this.contributionService.updateCountry(id, country);
    }

    @PatchMapping("/{id}/photo")
    public void updatePhoto(@PathVariable Long id, @RequestParam String photoURL) {
        this.contributionService.updatePhoto(id, photoURL);
    }

    @PatchMapping("/{id}/description")
    public void updateDescription(@PathVariable Long id, @RequestParam String description) {
        this.contributionService.updateDescription(id, description);

    }

    @PatchMapping("/{id}/sugarDegree")
    public void updateSugarDegree(@PathVariable Long id, @Valid @RequestParam double sugarDegree) {
        this.contributionService.updateSugarDegree(id, sugarDegree);
    }

    @PatchMapping("/{id}/quantity")
    public void updateQuantity(@PathVariable Long id, @Valid @RequestParam double quantity) {
        this.contributionService.updateQuantity(id, quantity);
    }
/*
    @GetMapping(params = "origin")
    public List<Contribution> getByOrigin(@Valid @RequestParam String origin) {
        return this.contributionService.getByOrigin(origin);
    }

    @GetMapping(params = "country")
    public List<Contribution> getByCountry(@Valid @RequestParam String country) {
        return this.contributionService.getByCountry(country);
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegree(@Valid @RequestParam double sugarDegree) {
        return this.contributionService.getBySugarDegree(sugarDegree);
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantity(@Valid @RequestParam double quantity) {
        return this.contributionService.getByQuantity(quantity);
    }


    @GetMapping(params = "associatedGrapeType")
    public List<Contribution> getByAssociatedGrapeType(@Valid @RequestParam GrapeType grapeType) {
        return this.contributionService.getByAssociatedGrapeType(grapeType);
    }
*/
}
