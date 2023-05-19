package it.unimol.vino.controllers;

import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.CancelProgressRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.ProgressProcessRequest;
import it.unimol.vino.services.ProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/process")
@RequiredArgsConstructor
public class ProcessController {
    private final ProcessService processService;

    @PostMapping
    public ResponseEntity<String> newProcess(@RequestBody @Valid NewProcessRequest request) {
        return ResponseEntity.ok("Processo con Id " +
                this.processService.createNewProcess(request) + " creato con successo");
    }

    @PutMapping("/addState")
    public ResponseEntity<String> addStates(@RequestBody @Valid AddStateToProcessRequest request) {
        this.processService.addState(request);
        return ResponseEntity.ok("Stato " +
                request.getStateId() + " aggiunto al processo " +
                request.getProcessId() + " con successo");
    }

    @PostMapping("/{process_id}/progress")
    public ResponseEntity<String> progressProcess(
            @PathVariable("process_id") Long processId,
            @RequestBody ProgressProcessRequest request) {
        return ResponseEntity.ok(
                this.processService.progressState(processId, request));
    }

    @PostMapping("/{process_id}/abort")
    public ResponseEntity<String> cancelProcess(
            @PathVariable("process_id") Long processId,
            @RequestBody CancelProgressRequest request) {
        this.processService.abortProcess(processId, request.getDescription());
        return ResponseEntity.ok("Processo " +
                processId + " annullato con successo");
    }

    @GetMapping
    public ResponseEntity<List<ProcessDTO>> getAllProcesses() {
        return ResponseEntity.ok(this.processService.getAllProcesses());
    }

    @GetMapping("{process_id}")
    public ResponseEntity<ProcessDTO> getProcess(@PathVariable("process_id") Long processId) {
        return ResponseEntity.ok(this.processService.getProcess(processId));
    }

    @GetMapping("{process_id}/states")
    public ResponseEntity<List<StateDTO>> getStates(@PathVariable("process_id") Long processId) {
        return ResponseEntity.ok(this.processService.getProcessStates(processId));
    }

}
