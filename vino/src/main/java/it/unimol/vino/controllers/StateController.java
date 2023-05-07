package it.unimol.vino.controllers;

import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.services.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/state")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @PostMapping
    public ResponseEntity<String> newState(@RequestBody @Valid NewStateRequest request) {
        return ResponseEntity.ok("Stato " + this.stateService.newState(request).getName()
                + " creato con successo"
        );
    }

    @GetMapping
    public ResponseEntity<List<StateDTO>> getAllStates() {
        return ResponseEntity.ok(this.stateService.getAllStates());
    }
}
