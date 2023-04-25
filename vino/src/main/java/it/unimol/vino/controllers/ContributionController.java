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
        try {
            return this.contributionService.get(id);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
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
        try{
            return this.contributionService.replace(id, contribution);
        }catch(EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updateOrigin(@PathVariable Long id, @Valid @RequestParam String origin){
        try{
            this.contributionService.updateOrigin(id, origin);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updateCountry(@PathVariable Long id, @Valid @RequestParam String country){
        try{
            this.contributionService.updateCountry(id, country);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updatePhoto(@PathVariable Long id, @RequestParam String photoURL){
        try{
            this.contributionService.updatePhoto(id, photoURL);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updateDescription(@PathVariable Long id, @RequestParam String description){
        try{
            this.contributionService.updateDescription(id, description);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updateSugarDegree(@PathVariable Long id, @Valid @RequestParam double sugarDegree){
        try{
            this.contributionService.updateSugarDegree(id, sugarDegree);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updateQuantity(@PathVariable Long id, @Valid @RequestParam double quantity) {
        try{
            this.contributionService.updateQuantity(id, quantity);
        } catch (EntityNotFoundException e){
            throw new ContributionNotFoundException(e.getMessage());
        }
    }

    @GetMapping(params = "origin")
    public List<Contribution> getByOrigin(@Valid @RequestParam String origin){
        List<Contribution> contributions = this.contributionService.getByOrigin(origin);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con origine " + origin);

        return contributions;
    }

    @GetMapping(params = "country")
    public List<Contribution> getByCountry(@Valid @RequestParam String country){
        List<Contribution> contributions = this.contributionService.getByCountry(country);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con origine " + country);

        return contributions;
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegree(@Valid @RequestParam double sugarDegree){
        List<Contribution> contributions = this.contributionService.getBySugarDegree(sugarDegree);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con grado di zuccheratura pari a " + sugarDegree);

        return contributions;
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegreeLessThanEqual(@Valid @RequestParam double sugarDegree){
        List<Contribution> contributions = this.contributionService.getBySugarDegreeLessThanEqual(sugarDegree);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con grado di zuccheratura pari o inferiore a " + sugarDegree);

        return contributions;
    }

    @GetMapping(params = "sugarDegree")
    public List<Contribution> getBySugarDegreeGreaterThanEqual(@Valid @RequestParam double sugarDegree){
        List<Contribution> contributions = this.contributionService.getBySugarDegreeGreaterThanEqual(sugarDegree);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con grado di zuccheratura pari o superiore a " + sugarDegree);

        return contributions;
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantity(@Valid @RequestParam double quantity){
        List<Contribution> contributions = this.contributionService.getByQuantity(quantity);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con quantità pari a " + quantity);

        return contributions;
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantityLessThanEqual(@Valid @RequestParam double quantity){
        List<Contribution> contributions = this.contributionService.getByQuantityLessThanEqual(quantity);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con quantità pari o inferiore a " + quantity);

        return contributions;
    }

    @GetMapping(params = "quantity")
    public List<Contribution> getByQuantityGreaterThanEqual(@Valid @RequestParam double quantity){
        List<Contribution> contributions = this.contributionService.getByQuantityGreaterThanEqual(quantity);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con quantità pari o superiore a " + quantity);

        return contributions;
    }

    @GetMapping(params = "associatedGrapeType")
    public List<Contribution> getByAssociatedGrapeType(@Valid @RequestParam GrapeType grapeType){
        List<Contribution> contributions = this.contributionService.getByAssociatedGrapeType(grapeType);
        if(contributions.isEmpty())
            throw new ContributionNotFoundException("Non esistono conferimenti con tipo d'uva " + grapeType);

        return contributions;
    }

}
