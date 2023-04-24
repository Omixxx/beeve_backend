package it.unimol.vino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.services.GrapeTypeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/grapetype")
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
    public void deleteAll(){
        this.grapeTypeService.delete();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrapeType> get(@PathVariable String id){
        GrapeType grapeType = this.grapeTypeService.get(id);

        if(grapeType == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(grapeType);
    }

    @PostMapping
    public GrapeType put(@RequestBody GrapeType grapeType){
        return this.grapeTypeService.put(grapeType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String name){
        this.grapeTypeService.delete(name);
    }

    @PutMapping("/{id}")
    public GrapeType replace(@PathVariable String name, @RequestBody GrapeType grapeType){
        return this.grapeTypeService.replace(name, grapeType);
    }

    @PatchMapping("/{id}")
    public void updateColor(@PathVariable String name, @Valid @RequestParam String grapeColor){
        this.grapeTypeService.updateColor(name, grapeColor);
    }
    
    @PatchMapping("/{id}")
    public void updateSpecies(@PathVariable String name, @Valid @RequestParam String grapeSpecies){
        this.grapeTypeService.updateSpecies(name, grapeSpecies);
    }

    @GetMapping(params = "grapeColor")
    public List<GrapeType> findByColor(@Valid @RequestParam String grapeColor){
        return this.grapeTypeService.findByColor(grapeColor);
    }

    @GetMapping(params = "species")
    public List<GrapeType> findBySpecies(@Valid @RequestParam String species){
        return this.grapeTypeService.findBySpecies(species);
    }
}
