package it.unimol.vino.services;

import java.util.List;

import it.unimol.vino.exceptions.GrapeTypeNotFoundException;
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
            return this.grapeType.findAll();
    }

    public GrapeType get(String id){
        return this.grapeType.findById(id)
                .orElseThrow( () -> new GrapeTypeNotFoundException("Non esiste alcun tipo d'uva con nome " + id));
    }

    public List<GrapeType> findByColor(String color){
        return this.grapeType.findByColor(color)
                .orElseThrow( () -> new GrapeTypeNotFoundException("Non esiste alcun tipo d'uva con colore " + color));
    }

    public List<GrapeType> findBySpecies(String species){
        return this.grapeType.findBySpecies(species)
                .orElseThrow( () -> new GrapeTypeNotFoundException("Il tipo d'uva con specie " + species + " non esiste"));
    }


    public GrapeType put(@Valid GrapeType grapeType){
        return this.grapeType.save(grapeType);
    }

    public GrapeType replace(String id, @Valid GrapeType grapeType){

        if(!this.grapeType.existsById(id)){
            throw new GrapeTypeNotFoundException("Il tipo d'uva con nome " + id + " non esiste");
        }
    
        grapeType.setId(id);

        return this.grapeType.save(grapeType);
    }


    public void updateColor(String id, String grapeColor){

        GrapeType grapeType = this.grapeType.findById(id).orElseThrow( () -> new GrapeTypeNotFoundException("Il tipo d'uva con nome " + id + " non esiste") );
        grapeType.setColor(grapeColor);

    }

    public void updateSpecies(String id, String species){

        GrapeType grapeType = this.grapeType.findById(id).orElseThrow( () -> new GrapeTypeNotFoundException("Il tipo d'uva con nome " + id + " non esiste") );
        grapeType.setSpecies(species);
    }


    public void delete(String id){
        this.grapeType.delete(id);
    }

    public void deleteAllByColor(String color){
        this.grapeType.deleteAllByColor(color);
    }

    public void deleteAllBySpecies(String species){
        this.grapeType.deleteAllBySpecies(species);
    }

    public void delete(){
        this.grapeType.deleteAll();
    }

}
