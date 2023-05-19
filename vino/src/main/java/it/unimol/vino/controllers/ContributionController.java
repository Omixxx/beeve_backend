package it.unimol.vino.controllers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.UserDTO;

import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.services.ContributionService;




import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    @GetMapping
    public ResponseEntity<List<ContributionDTO>> getAll() {
        return ResponseEntity.ok(this.contributionService.getAll());
    }

    @PostMapping
    public ResponseEntity<String> put(@ModelAttribute RegisterContributionRequest request) {
        return ResponseEntity.ok(this.contributionService.put(request).orElseThrow());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributionDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.contributionService.get(id));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(this.contributionService.getUser(id));
    }
}
