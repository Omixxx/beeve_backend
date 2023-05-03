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

import static org.springframework.http.ResponseEntity.ok;

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
        this.grapeTypeService.delete();
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
        this.grapeTypeService.delete(name);
    }

    @PostMapping
    public GrapeType put(@RequestBody GrapeType grapeType){
        return this.grapeTypeService.put(grapeType);
    }

    @GetMapping("/{name}")
    public ResponseEntity<GrapeType> get(@PathVariable String name){
        return ResponseEntity.ok(this.grapeTypeService.get(name));
    }

    @GetMapping(params = "grapeColor")
    public List<GrapeType> findByColor(@Valid @RequestParam String grapeColor){
        return this.grapeTypeService.findByColor(grapeColor);
    }

    @GetMapping(params = "species")
    public List<GrapeType> findBySpecies(@Valid @RequestParam String species){
        return this.grapeTypeService.findBySpecies(species);
    }

    @PutMapping("/{name}")
    public GrapeType replace(@PathVariable String name, @RequestBody GrapeType grapeType){
            return this.grapeTypeService.replace(name, grapeType);
    }

    @PatchMapping("/{name}")
    public void updateColor(@PathVariable String name, @Valid @RequestParam String grapeColor){
            this.grapeTypeService.updateColor(name, grapeColor);
    }

    @PatchMapping("/{name}")
    public void updateSpecies(@PathVariable String name, @Valid @RequestParam String grapeSpecies){
            this.grapeTypeService.updateSpecies(name, grapeSpecies);
    }

}
