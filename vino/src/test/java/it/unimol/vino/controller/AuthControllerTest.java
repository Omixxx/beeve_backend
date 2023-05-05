/*package it.unimol.vino.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import it.unimol.vino.controllers.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.request.AuthenticationRequest;
import it.unimol.vino.models.request.RegisterRequest;
import it.unimol.vino.models.response.AuthenticationResponse;
import it.unimol.vino.services.AuthService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerTest {

    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }
    

    @Test
    void register_withValidRequest_shouldReturn200OK() throws PasswordNotValidException, UserAlreadyRegistered {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(new AuthenticationResponse("token"));

        ResponseEntity<AuthenticationResponse> responseEntity = authController.register(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("token", responseEntity.getBody().getToken());

        // verify that the service method was called once with the expected argument
        verify(authService, times(1)).register(request);
    }

    @Test
    void register_withInvalidPassword_shouldThrowException() throws PasswordNotValidException, UserAlreadyRegistered {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("invalid")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenThrow(PasswordNotValidException.class);

        assertThrows(PasswordNotValidException.class, () -> authController.register(request));

        // verify that the service method was called once with the expected argument
        verify(authService, times(1)).register(request);
    }

    @Test
    void register_withExistingUser_shouldThrowException() throws PasswordNotValidException, UserAlreadyRegistered {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenThrow(UserAlreadyRegistered.class);

        assertThrows(UserAlreadyRegistered.class, () -> authController.register(request));

        // verify that the service method was called once with the expected argument
        verify(authService, times(1)).register(request);
    }

    @Test
    void authenticate_withValidRequest_shouldReturn200OK() {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(new AuthenticationResponse("token"));

        ResponseEntity<AuthenticationResponse> responseEntity = authController.authenticate(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("token", responseEntity.getBody().getToken());

        // verify that the service method was called once with the expected argument
        verify(authService, times(1)).authenticate(request);
    }
}*/