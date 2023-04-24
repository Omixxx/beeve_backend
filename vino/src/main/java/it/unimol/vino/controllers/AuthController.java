package it.unimol.vino.controllers;

import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.request.AuthenticationRequest;
import it.unimol.vino.models.request.RegisterRequest;
import it.unimol.vino.models.response.AuthenticationResponse;
import it.unimol.vino.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest)
            throws PasswordNotValidException, UserAlreadyRegistered {
        return ResponseEntity.ok(this.authService.register(registerRequest));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(this.authService.authenticate(authenticationRequest));
    }


}
