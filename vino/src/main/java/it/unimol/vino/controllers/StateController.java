package it.unimol.vino.controllers;

import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.services.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/state")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @PostMapping
    public ResponseEntity<String> newState(@RequestBody @Valid NewStateRequest request) {
        return ResponseEntity.ok("Stato con id " + this.stateService.newState(request)
                + " creato con successo"
        );
    }
}
