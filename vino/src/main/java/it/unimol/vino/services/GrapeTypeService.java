package it.unimol.vino.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.GrapeTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Validated
public class GrapeTypeService {
    
    private final GrapeTypeRepository grapeType;

    public GrapeTypeService(GrapeTypeRepository grapeType) {
        this.grapeType = grapeType;
    }

    public List<GrapeType> getAll(){
            return (List<GrapeType>) this.grapeType.findAll();
    }
    

    public GrapeType get(String name){
        return this.grapeType.findById(name);
    }

    public List<GrapeType> findByColor(String color){
        return this.grapeType.findByColor(color);
    }

    public List<GrapeType> findBySpecies(String species){
        return this.grapeType.findBySpecies(species);
    }

    public GrapeType put(@Valid GrapeType grapeType){
        return this.grapeType.save(grapeType);
    }

    public GrapeType replace(String name, @Valid GrapeType grapeType){

        if(!this.grapeType.existsById(name)){
            throw new EntityNotFoundException("Il tipo d'uva con nome " + name + " non esiste");
        }
    
        grapeType.setName(name);

        return this.grapeType.save(grapeType);
    }


    public void updateColor(String name, String grapeColor){

        if(!this.grapeType.existsById(name)){
            throw new EntityNotFoundException("Il tipo d'uva con nome " + name + " non esiste");
        }
        
        this.grapeType.findById(name).setGrapeColor(grapeColor);

    }

    public void updateSpecies(String name, String species){
    
        if(!this.grapeType.existsById(name)){
            throw new EntityNotFoundException("Il tipo d'uva con nome " + name + " non esiste");
        }

        this.grapeType.findById(name).setSpecies(species);
    }


    public void delete(String name){
        this.grapeType.deleteById(name);
    }

    public void delete(){
        this.grapeType.deleteAll();
    }

}
