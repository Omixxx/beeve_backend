package it.unimol.vino.controllers;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.UserDTO;

import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.services.ContributionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    @GetMapping
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<List<ContributionDTO>> getAll() {
        return ResponseEntity.ok(this.contributionService.getAll());
    }

    @PostMapping
    @RequirePermissions(value = {PermissionType.WRITE}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<String> put(@ModelAttribute RegisterContributionRequest request) {
        return ResponseEntity.ok(this.contributionService.put(request));
    }

    @GetMapping("/{id}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<ContributionDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.contributionService.get(id));
    }

    @GetMapping("user/{id}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.CONFERIMENTO)
    public ResponseEntity<UserDTO> getSubmitter(@PathVariable Long id) {
        return ResponseEntity.ok(this.contributionService.getSubmitter(id));
    }
}
