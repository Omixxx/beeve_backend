package it.unimol.vino.controllers;

import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.request.AuthenticationRequest;
import it.unimol.vino.models.request.RegisterUserRequest;
import it.unimol.vino.models.response.AuthenticationResponse;
import it.unimol.vino.services.AuthService;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterUserRequest registerUserRequest,
            HttpServletRequest servletRequest
    ) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting user registration");
        return ResponseEntity.ok(this.authService.register(registerUserRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest,
            HttpServletRequest servletRequest
    ) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting user authentication");
        return ResponseEntity.ok(this.authService.authenticate(authenticationRequest));
    }
}
