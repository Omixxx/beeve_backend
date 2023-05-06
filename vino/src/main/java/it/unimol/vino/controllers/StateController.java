package it.unimol.vino.controllers;

import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.services.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
