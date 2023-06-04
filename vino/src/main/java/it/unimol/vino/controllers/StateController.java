package it.unimol.vino.controllers;

import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.services.StateService;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/state")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @PostMapping
    public ResponseEntity<String> newState(@RequestBody @Valid NewStateRequest request, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is creating a new state");
        return ResponseEntity.ok(
                "Stato " + this.stateService.newState(request).getName()
                        + " creato con successo"
        );
    }

    @GetMapping
    public ResponseEntity<List<StateDTO>> getAllStates(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all states");
        return ResponseEntity.ok(this.stateService.getAllStates());
    }

}
