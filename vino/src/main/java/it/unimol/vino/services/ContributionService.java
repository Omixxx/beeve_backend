package it.unimol.vino.services;

import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.ContributionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ContributionService {

    private final ContributionRepository contribution;

    public ContributionService(ContributionRepository contribution) {
        this.contribution = contribution;
    }

    public List<Contribution> getAll(){
        return this.contribution.findAll();
    }

    public Contribution get(Long id){
        return this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste") );
    }

    public List<Contribution> getByOrigin(String origin){
        return this.contribution.findByOrigin(origin);
    }

    public List<Contribution> getByCountry(String country){
        return this.contribution.findByCountry(country);
    }

    public List<Contribution> getBySugarDegree(double sugarDegree){
        return this.contribution.findBySugarDegree(sugarDegree);
    }

    public List<Contribution> getBySugarDegreeGreaterThanEqual(double sugarDegree){
        return this.contribution.findBySugarDegreeGreaterThanEqual(sugarDegree);
    }

    public List<Contribution> getBySugarDegreeLessThanEqual(double sugarDegree){
        return this.contribution.findBySugarDegreeLessThanEqual(sugarDegree);
    }

//    public List<Contribution> getBySugarDegreeBetween(double sugarDegreeMin, double sugarDegreeMax){
//        return this.contribution.findBySugarDegreeBetween(sugarDegreeMin, sugarDegreeMax);
//    }

    public List<Contribution> getByQuantity(double quantity){
        return this.contribution.findByQuantity(quantity);
    }

    public List<Contribution> getByQuantityGreaterThanEqual(double quantity){
        return this.contribution.findByQuantityGreaterThanEqual(quantity);
    }

    public List<Contribution> getByQuantityLessThanEqual(double quantity){
        return this.contribution.findByQuantityLessThanEqual(quantity);
    }

//    public List<Contribution> getByQuantityBetween(double quantityMin, double quantityMax){
//        return this.contribution.findByQuantityBetween(quantityMin, quantityMax);
//    }

    public List<Contribution> getByAssociatedGrapeType(GrapeType grapeType){
        return this.contribution.findByAssociatedGrapeType(grapeType);
    }

    public Contribution put(@Valid Contribution contribution){
        return this.contribution.save(contribution);
    }

    public Contribution replace(Long id, @Valid Contribution contribution){

        if(!this.contribution.existsById(id)){
            throw new EntityNotFoundException("Il conferimento con id " + id + " non esiste");
        }

        contribution.setId(id);

        return this.contribution.save(contribution);
    }

    public void updateOrigin(Long id, String origin){
        this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste")).setOrigin(origin);
    }

    public void updateCountry(Long id, String country){
        this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste")).setCountry(country);
    }

    public void updatePhoto(Long id, String URL){
        this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste")).setPhotoURL(URL);
    }

    public void updateDescription(Long id, String description){
        this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste")).setDescription(description);
    }

    public void updateSugarDegree(Long id, double sugarDegree){
        this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste")).setSugarDegree(sugarDegree);
    }

    public void updateQuantity(Long id, double quantity){
        this.contribution.findById(id).orElseThrow( () -> new EntityNotFoundException("Il conferimento con id " + id + " non esiste")).setQuantity(quantity);
    }

    public void delete(){
        this.contribution.deleteAll();
    }

    public void delete(Long id){
        this.contribution.deleteById(id);
    }

    public void deleteAllByOrigin(String origin){
        this.contribution.deleteAllByOrigin(origin);
    }

    public void deleteAllByCountry(String country){
        this.contribution.deleteAllByCountry(country);
    }

    public void deleteAllBySugarDegree(double sugarDegree){
        this.contribution.deleteAllBySugarDegree(sugarDegree);
    }

    public void deleteAllBySugarDegreeGreaterThanEqual(double sugarDegree){
        this.contribution.deleteAllBySugarDegreeGreaterThanEqual(sugarDegree);
    }

    public void deleteAllBySugarDegreeLessThanEqual(double sugarDegree){
        this.contribution.deleteAllBySugarDegreeLessThanEqual(sugarDegree);
    }

    public void deleteAllByQuantity(double quantity){
        this.contribution.deleteAllByQuantity(quantity);
    }

    public void deleteAllByQuantityGreaterThanEqual(double quantity){
        this.contribution.deleteAllByQuantityGreaterThanEqual(quantity);
    }

    public void deleteAllByQuantityLessThanEqual(double quantity){
        this.contribution.deleteAllByQuantityLessThanEqual(quantity);
    }

    public void deleteAllByAssociatedGrapeType(GrapeType grapeType){
        this.contribution.deleteAllByAssociatedGrapeType(grapeType);
    }
}
