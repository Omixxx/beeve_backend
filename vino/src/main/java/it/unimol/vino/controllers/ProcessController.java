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

    @PutMapping("/addState")
    public ResponseEntity<String> addStates(@RequestBody @Valid AddStateToProcessRequest request) {
        this.processService.addState(request);
        return ResponseEntity.ok("Stato " +
                request.getStateId() + " aggiunto al processo " +
                request.getProcessId() + " con successo"
        );
    }

    @PostMapping("/{process_id}/start")
    public ResponseEntity<String> startProcess(@PathVariable("process_id") Long processId) {
        this.processService.startProcess(processId);
        return ResponseEntity.ok("Processo " +
                processId + " avviato con successo"
        );
    }

    @PostMapping("/{process_id}/progress")
    public ResponseEntity<String> progressProcess(@PathVariable("process_id") Long processId) {
        return ResponseEntity.ok("Processo " +
                processId + " avanzato con successo verso lo stato " +
                this.processService.progressState(processId)
        );
    }

}
