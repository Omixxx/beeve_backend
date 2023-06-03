package it.unimol.vino.controllers;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.dto.ProcessDTO;
import it.unimol.vino.dto.StateDTO;
import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.CancelProgressRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.ProgressProcessRequest;
import it.unimol.vino.models.response.CompletedStateResponse;
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
    @RequirePermissions(value = {PermissionType.WRITE}, sector = SectorName.PROCESSO)
    public ResponseEntity<String> newProcess(@RequestBody @Valid NewProcessRequest request) {
        return ResponseEntity.ok("Processo con Id " +
                this.processService.createNewProcess(request) + " creato con successo");
    }

    @PostMapping("/{process_id}/progress")
    @RequirePermissions(value = {PermissionType.UPDATE}, sector = SectorName.PROCESSO)
    public ResponseEntity<String> progressProcess(
            @PathVariable("process_id") Long processId,
            @RequestBody ProgressProcessRequest request) {
        return ResponseEntity.ok(
                this.processService.progressState(processId, request));
    }

    @PostMapping("/{process_id}/abort")
    @RequirePermissions(value = {PermissionType.DELETE}, sector = SectorName.PROCESSO)
    public ResponseEntity<String> cancelProcess(
            @PathVariable("process_id") Long processId,
            @RequestBody CancelProgressRequest request) {
        this.processService.abortProcess(processId, request.getDescription());
        return ResponseEntity.ok("Processo " +
                processId + " annullato con successo");
    }

    @GetMapping
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.PROCESSO)
    public ResponseEntity<List<ProcessDTO>> getAllProcesses() {
        return ResponseEntity.ok(this.processService.getAllProcesses());
    }

    @GetMapping("{process_id}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.PROCESSO)
    public ResponseEntity<ProcessDTO> getProcess(@PathVariable("process_id") Long processId) {
        return ResponseEntity.ok(this.processService.getProcess(processId));
    }

    @GetMapping("{process_id}/states")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.PROCESSO)
    public ResponseEntity<List<StateDTO>> getStates(@PathVariable("process_id") Long processId) {
        return ResponseEntity.ok(this.processService.getProcessStates(processId));
    }

    @GetMapping("{process_id}/state/{state_id}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.PROCESSO)
    public ResponseEntity<CompletedStateResponse> getState(
            @PathVariable("process_id") Long processId,
            @PathVariable("state_id") Long stateId
    ) {
        return ResponseEntity.ok(this.processService.getCompletedState(processId, stateId));
    }

    @GetMapping("{process_id}/grape/used_quantity")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.PROCESSO)
    public ResponseEntity<Double> getGrapeUsedQuantity(@PathVariable("process_id") Long processId) {
        return ResponseEntity.ok(this.processService.getGrapeUsedInProcess(processId));
    }

}
