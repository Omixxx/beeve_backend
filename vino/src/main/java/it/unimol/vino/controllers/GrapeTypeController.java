package it.unimol.vino.controllers;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.services.GrapeTypeService;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/grape-type")

public class GrapeTypeController {

    private final GrapeTypeService grapeTypeService;

    @GetMapping
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<List<GrapeTypeDTO>> getAll() {
        return ResponseEntity.ok(this.grapeTypeService.getAll());
    }

    @PostMapping
    @RequirePermissions(value = {PermissionType.WRITE}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<GrapeTypeDTO> put(@RequestBody GrapeType grapeType) {
        return ResponseEntity.ok(this.grapeTypeService.put(grapeType));
    }

    @GetMapping("/{species}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<List<GrapeTypeDTO>> get(@PathVariable String species) {
        return ResponseEntity.ok(this.grapeTypeService.getAllBySpecies(species));
    }
}
