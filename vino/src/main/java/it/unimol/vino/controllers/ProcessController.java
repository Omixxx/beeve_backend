package it.unimol.vino.controllers;

import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.SetStateRequest;
import it.unimol.vino.models.request.SetWasteRequest;
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

    @PostMapping("/new")
    public ResponseEntity<String> newProcess(@RequestBody @Valid NewProcessRequest newProcessRequest)
        {
        return ResponseEntity.ok(this.processService.newProductionProcess(newProcessRequest));
    }

    @PutMapping("/setState")
    public ResponseEntity<String> setState(@RequestBody @Valid SetStateRequest setStateRequest)
        {
        return ResponseEntity.ok(this.processService.setState(setStateRequest));
    }

    @PutMapping("/addState")
    public ResponseEntity<String> addStates(@RequestBody @Valid AddStateToProcessRequest addStateToProcessRequest) throws Exception {
        return ResponseEntity.ok(this.processService.addState(addStateToProcessRequest));
    }

}
