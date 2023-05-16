package it.unimol.vino.service;

import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.request.RegisterRequest;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterWithInvalidPassword() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("weak")
                .build();

        assertThrows(PasswordNotValidException.class, () -> authService.register(request));
    }

    @Test
    void testRegisterWithAlreadyRegisteredUser() {

        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("pAssw0rd")
                .build();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password("encodedPassword")
                .permissions(new ArrayList<>())
                .role(Role.USER)
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
    }


}




