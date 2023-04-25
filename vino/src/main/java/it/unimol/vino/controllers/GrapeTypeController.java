package it.unimol.vino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unimol.vino.exceptions.GrapeTypeNotFoundException;

import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.services.GrapeTypeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/grape-type")
public class GrapeTypeController {
    
    private final GrapeTypeService grapeTypeService;

    public GrapeTypeController(GrapeTypeService grapeTypeService) {
        this.grapeTypeService = grapeTypeService;
    }

    @GetMapping
    public List<GrapeType> getAll(){
        return this.grapeTypeService.getAll();
    }

    @DeleteMapping
    public void delete(){
        this.grapeTypeService.deleteByName();
    }

    @DeleteMapping(params = "color")
    public void deleteByColor(@Valid @RequestParam String color){
        this.grapeTypeService.deleteAllByColor(color);
    }

    @DeleteMapping(params = "species")
    public void deleteBySpecies(@Valid @RequestParam String species){
        this.grapeTypeService.deleteAllBySpecies(species);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name){
        this.grapeTypeService.deleteByName(name);
    }

    @PostMapping
    public GrapeType put(@RequestBody GrapeType grapeType){
        return this.grapeTypeService.put(grapeType);
    }

    @GetMapping("/{name}")
    public ResponseEntity<GrapeType> get(@PathVariable String name){
        GrapeType grapeType = this.grapeTypeService.get(name);

        if(grapeType == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(grapeType);
    }

    @GetMapping(params = "grapeColor")
    public List<GrapeType> findByColor(@Valid @RequestParam String grapeColor){
        List<GrapeType> grapesByColor = this.grapeTypeService.findByColor(grapeColor);
        if(grapesByColor.isEmpty())
            throw new GrapeTypeNotFoundException("Non ci sono tipi d'uva con il colore " + grapeColor);

        return grapesByColor;
    }

    @GetMapping(params = "species")
    public List<GrapeType> findBySpecies(@Valid @RequestParam String species){
        List<GrapeType> grapesBySpecies = this.grapeTypeService.findBySpecies(species);
        if(grapesBySpecies.isEmpty())
            throw new GrapeTypeNotFoundException("Non ci sono tipi d'uva con la specie " + species);

        return grapesBySpecies;
    }

    @PutMapping("/{name}")
    public GrapeType replace(@PathVariable String name, @RequestBody GrapeType grapeType){
        try{
            return this.grapeTypeService.replace(name, grapeType);
        }catch(EntityNotFoundException e){
            throw new GrapeTypeNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{name}")
    public void updateColor(@PathVariable String name, @Valid @RequestParam String grapeColor){
        try{
            this.grapeTypeService.updateColor(name, grapeColor);
        }catch(EntityNotFoundException e){
            throw new GrapeTypeNotFoundException(e.getMessage());
        }
    }

    @PatchMapping("/{name}")
    public void updateSpecies(@PathVariable String name, @Valid @RequestParam String grapeSpecies){
        try {
            this.grapeTypeService.updateSpecies(name, grapeSpecies);
        } catch (EntityNotFoundException e) {
            throw new GrapeTypeNotFoundException(e.getMessage());
        }

    }

}
