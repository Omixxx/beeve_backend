package it.unimol.vino.services;

import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.request.AuthenticationRequest;
import it.unimol.vino.models.request.RegisterRequest;
import it.unimol.vino.models.response.AuthenticationResponse;
import it.unimol.vino.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(@Valid RegisterRequest request) throws PasswordNotValidException {
        if (!isPasswordValid(request.getPassword())) {
            throw new PasswordNotValidException("Password not valid, it must be between 8 and 30 characters, " +
                    "and must contain at least one uppercase letter, one lowercase letter and one number)");
        }

        if(this.userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyRegistered("Email already in use");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        this.userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = this.userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private boolean isPasswordValid(@NotNull String password) {
        return password.length() >= 8 &&
                password.length() <= 30 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*");
    }
}
