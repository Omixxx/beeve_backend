package it.unimol.vino.controllers;

import it.unimol.vino.dto.ProviderDTO;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.models.request.UpdateProviderRequest;
import it.unimol.vino.services.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping()
    public ResponseEntity<List<Provider>> getAllProviders() {
        return ResponseEntity.ok(this.providerService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProviderDTO> getProviderByName(@PathVariable String name) {
        return ResponseEntity.ok(this.providerService.getProviderByName(name));
    }

    @GetMapping("/book")
    public ResponseEntity<List<ProviderDTO>> getProviderBook() {
        return ResponseEntity.ok(this.providerService.getProviderBook());
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid RegisterProviderRequest registerProviderRequest)
            throws UserAlreadyRegistered {

        return ResponseEntity.ok(this.providerService.providerRegister(registerProviderRequest));
    }

    @PostMapping("/updateProvider")
    public ResponseEntity<String> updateProvider(@RequestBody @Valid UpdateProviderRequest request) {
        return ResponseEntity.ok(this.providerService.updateProvider(request));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteProvider(@PathVariable String name) {
        return ResponseEntity.ok(this.providerService.changeVisibility(name));
    }
}
