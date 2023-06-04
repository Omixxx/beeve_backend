package it.unimol.vino.controllers;

import it.unimol.vino.dto.ProviderDTO;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.models.request.UpdateProviderRequest;
import it.unimol.vino.services.ProviderService;
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
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping()
    public ResponseEntity<List<Provider>> getAllProviders(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all providers");
        return ResponseEntity.ok(this.providerService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProviderDTO> getProviderByName(@PathVariable String name, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting a provider");
        return ResponseEntity.ok(this.providerService.getProviderByName(name));
    }

    @GetMapping("/book")
    public ResponseEntity<List<ProviderDTO>> getProviderBook(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all providers");
        return ResponseEntity.ok(this.providerService.getProviderBook());
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid RegisterProviderRequest registerProviderRequest, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is registering a new provider");

        return ResponseEntity.ok(this.providerService.providerRegister(registerProviderRequest));
    }

    @PostMapping("/updateProvider")
    public ResponseEntity<String> updateProvider(@RequestBody @Valid UpdateProviderRequest request, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is updating a provider");
        return ResponseEntity.ok(this.providerService.updateProvider(request));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteProvider(@PathVariable String name, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is deleting a provider");
        return ResponseEntity.ok(this.providerService.changeVisibility(name));
    }
}
