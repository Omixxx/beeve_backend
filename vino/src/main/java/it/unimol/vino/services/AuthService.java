package it.unimol.vino.services;

import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.request.AuthenticationRequest;
import it.unimol.vino.models.request.RegisterRequest;
import it.unimol.vino.models.response.AuthenticationResponse;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.PasswordValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SectorRepository sectorRepository;

    @Transactional
    public AuthenticationResponse register(@Valid RegisterRequest request) throws PasswordNotValidException {
        if (PasswordValidator.isPasswordNotValid(request.getPassword())) {
            throw new PasswordNotValidException(PasswordValidator.ERROR_MESSAGE);
        }

        if (this.userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyRegistered("Email gia in uso");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .age(request.getAge())
                .password(passwordEncoder.encode(request.getPassword()))
                .permissions(new ArrayList<>())
                .role(request.getIsAdmin() ? Role.ADMIN : Role.USER)
                .build();


        List<Sector> sectors = this.sectorRepository.findAll();
        sectors.forEach(user::addPermission);
        this.userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("L'utente con email " + request.getEmail() + " non esiste"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new PasswordNotValidException("credenziali non valide");

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
