package it.unimol.vino.controllers;

import it.unimol.vino.dto.ContributionDTO;
import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.services.ContributionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    @GetMapping
    public List<ContributionDTO> getAll() {
        return this.contributionService.getAll();
    }

    @PostMapping
    public String put(@RequestBody RegisterContributionRequest request) {
        return this.contributionService.put(request);
    }

    @GetMapping("/{id}")
    public ContributionDTO get(@PathVariable Long id) {
        return this.contributionService.get(id);
    }

    @GetMapping("user/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return this.contributionService.getUser(id);
    }
}
