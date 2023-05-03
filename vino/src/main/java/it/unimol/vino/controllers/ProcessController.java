package it.unimol.vino.controllers;

import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.services.ProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/process")
@RequiredArgsConstructor
public class ProcessController {
    private final ProcessService processService;

    @PostMapping
    public ResponseEntity<String> newProcess(@RequestBody @Valid NewProcessRequest request) {
        return ResponseEntity.ok("Processo con Id " +
                this.processService.createNewProcess(request) + " creato con successo"
        );
    }

    @PutMapping("/{processId}/progressState")
    public ResponseEntity<String> progressState(@PathVariable Long processId) {
        return ResponseEntity.ok("Processo " +
                this.processService.progressState(processId) + " aggiornato con successo"
        );
    }

    @PutMapping("/addState")
    public ResponseEntity<String> addStates(@RequestBody @Valid AddStateToProcessRequest request) {
        this.processService.addState(request);
        return ResponseEntity.ok("Stato " +
                request.getStateId() + " aggiunto al processo " +
                request.getProcessId() + " con successo"
        );
    }

}
