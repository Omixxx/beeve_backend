package it.unimol.vino.controllers;

import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
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
    public List<Contribution> getAll(){
        return this.contributionService.getAll();
    }

    @DeleteMapping
    public void delete(){
        this.contributionService.delete();
    }

    @PostMapping
    public Contribution put(@RequestBody Contribution contribution){
        return this.contributionService.put(contribution);
    }

    @GetMapping("/{id}")
    public Contribution get(@PathVariable Long id){
        return this.contributionService.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.contributionService.delete(id);
    }

    @DeleteMapping(params = "origin")
    public void deleteAllByOrigin(@Valid @RequestParam String origin){
        this.contributionService.deleteAllByOrigin(origin);
    }

    @DeleteMapping(params = "country")
    public void deleteAllByCountry(@Valid @RequestParam String country){
        this.contributionService.deleteAllByCountry(country);
    }

    @DeleteMapping(params = "sugarDegree")
    public void deleteAllBySugarDegree(@Valid @RequestParam double sugarDegree){
        this.contributionService.deleteAllBySugarDegree(sugarDegree);
    }

    @DeleteMapping(params = "sugarDegree")
    public void deleteAllBySugarDegreeGreaterThanEqual(@Valid @RequestParam double sugarDegree){
        this.contributionService.deleteAllBySugarDegreeGreaterThanEqual(sugarDegree);
    }

    @DeleteMapping(params = "sugarDegree")
    public void deleteAllBySugarDegreeLessThanEqual(@Valid @RequestParam double sugarDegree){
        this.contributionService.deleteAllBySugarDegreeLessThanEqual(sugarDegree);
    }

    @DeleteMapping(params = "quantity")
    public void deleteAllByQuantity(@Valid @RequestParam double quantity){
        this.contributionService.deleteAllByQuantity(quantity);
    }

    @DeleteMapping(params = "quantity")
    public void deleteAllByQuantityGreaterThanEqual(@Valid @RequestParam double quantity){
        this.contributionService.deleteAllByQuantityGreaterThanEqual(quantity);
    }

    @DeleteMapping(params = "quantity")
    public void deleteAllByQuantityLessThanEqual(@Valid @RequestParam double quantity){
        this.contributionService.deleteAllByQuantityLessThanEqual(quantity);
    }

    @DeleteMapping(params = "associatedGrapeType")
    public void deleteAllByAssociatedGrapeType(@Valid @RequestParam GrapeType associatedGrapeType){
        this.contributionService.deleteAllByAssociatedGrapeType(associatedGrapeType);
    }

    @PutMapping("/{id}")
    public Contribution replace(@PathVariable Long id, @RequestBody Contribution contribution){
        return this.contributionService.replace(id, contribution);
    }

    @PatchMapping("/{id}")
    public void updateOrigin(@PathVariable Long id, @Valid @RequestParam String origin){
            this.contributionService.updateOrigin(id, origin);
    }

    @PatchMapping("/{id}")
    public void updateCountry(@PathVariable Long id, @Valid @RequestParam String country){
            this.contributionService.updateCountry(id, country);
    }

    @PatchMapping("/{id}")
    public void updatePhoto(@PathVariable Long id, @RequestParam String photoURL){
            this.contributionService.updatePhoto(id, photoURL);
    }

    @PatchMapping("/{id}")
    public void updateDescription(@PathVariable Long id, @RequestParam String description){
            this.contributionService.updateDescription(id, description);

    }

    @PatchMapping("/{id}")
    public void updateSugarDegree(@PathVariable Long id, @Valid @RequestParam double sugarDegree){
            this.contributionService.updateSugarDegree(id, sugarDegree);
    }

    @PatchMapping("/{id}")
    public void updateQuantity(@PathVariable Long id, @Valid @RequestParam double quantity) {
            this.contributionService.updateQuantity(id, quantity);
    }

    @GetMapping(params = "origin")
    public List<Contribution> getByOrigin(@Valid @RequestParam String origin){
        return this.contributionService.getByOrigin(origin);
    }

    @GetMapping(params = "country")
    public List<Contribution> getByCountry(@Valid @RequestParam String country){
        return this.contributionService.getByCountry(country);
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegree(@Valid @RequestParam double sugarDegree){
        return this.contributionService.getBySugarDegree(sugarDegree);
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegreeLessThanEqual(@Valid @RequestParam double sugarDegree){
        return this.contributionService.getBySugarDegreeLessThanEqual(sugarDegree);
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegreeGreaterThanEqual(@Valid @RequestParam double sugarDegree){
        return this.contributionService.getBySugarDegreeGreaterThanEqual(sugarDegree);
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantity(@Valid @RequestParam double quantity){
        return this.contributionService.getByQuantity(quantity);
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantityLessThanEqual(@Valid @RequestParam double quantity){
        return this.contributionService.getByQuantityLessThanEqual(quantity);
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantityGreaterThanEqual(@Valid @RequestParam double quantity){
        return this.contributionService.getByQuantityGreaterThanEqual(quantity);
    }

    @GetMapping(params = "associatedGrapeType")
    public List<Contribution> getByAssociatedGrapeType(@Valid @RequestParam GrapeType grapeType){
        return this.contributionService.getByAssociatedGrapeType(grapeType);
    }

}
