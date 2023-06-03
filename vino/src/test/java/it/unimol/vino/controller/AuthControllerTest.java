package it.unimol.vino.controller;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthControllerTest {
/*
    private AuthController authController;
    private JwtService jwtService;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
        jwtService = new JwtService();
    }
    

    @Test
    void register_withValidRequest_shouldReturn200K() throws PasswordNotValidException, UserAlreadyRegistered {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();


       // when(authService.register(any(RegisterRequest.class))).thenReturn(new AuthenticationResponse("token"));

        ResponseEntity<AuthenticationResponse> responseEntity = authController.register(request);



        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().getToken());

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

 */
}