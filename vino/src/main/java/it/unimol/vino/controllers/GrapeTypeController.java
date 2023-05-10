package it.unimol.vino.controllers;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("api/v1/grape-type")

public class GrapeTypeController {

    private final GrapeTypeService grapeTypeService;

    @GetMapping
    public List<GrapeType> getAll() {
        return this.grapeTypeService.getAll();
    }

    @PostMapping
    public GrapeType put(@RequestBody GrapeType grapeType) {
        return this.grapeTypeService.put(grapeType);
    }

    @GetMapping("/{name}")
    public ResponseEntity<GrapeType> get(@PathVariable String name) {
        return ResponseEntity.ok(this.grapeTypeService.get(name));
    }
}
