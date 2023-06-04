package it.unimol.vino.controllers;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.services.GrapeTypeService;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/grape-type")

public class GrapeTypeController {

    private final GrapeTypeService grapeTypeService;

    @GetMapping
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<List<GrapeTypeDTO>> getAll(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all grape types");
        return ResponseEntity.ok(this.grapeTypeService.getAll());
    }

    @PostMapping
    @RequirePermissions(value = {PermissionType.WRITE}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<GrapeTypeDTO> put(@RequestBody GrapeType grapeType, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + "is registering a new grape type");
        return ResponseEntity.ok(this.grapeTypeService.put(grapeType));
    }

    @GetMapping("/{species}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<List<GrapeTypeDTO>> get(@PathVariable String species, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting a grape type");
        return ResponseEntity.ok(this.grapeTypeService.getAllBySpecies(species));
    }
}
