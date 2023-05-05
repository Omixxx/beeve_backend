/*package it.unimol.vino.controller;

import it.unimol.vino.controllers.AuthController;
import it.unimol.vino.exceptions.PasswordNotValidException;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.request.AuthenticationRequest;
import it.unimol.vino.models.response.AuthenticationResponse;
import it.unimol.vino.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Test
    void testAuthenticate() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("mario.rossi", "mario123");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(expectedResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

<<<<<<< HEAD
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().getToken());

        // verify that the service method was called once with the expected argument
        verify(authService, times(1)).register(request);
=======
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"mario.rossi\",\"password\":\"mario123\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(expectedResponse.getToken()))
                .andDo(MockMvcResultHandlers.print());
>>>>>>> Testing/Entity
    }

    @Test
    void testAuthenticateUserNotFound() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("mario.rossi", "mario123");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenThrow(UserNotFoundException.class);

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"mario.rossi\",\"password\":\"mario123\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testAuthenticateInvalidPassword() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("mario.rossi", "mario123");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenThrow(PasswordNotValidException.class);

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"mario.rossi\",\"password\":\"mario123\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
<<<<<<< HEAD

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
=======
>>>>>>> Testing/Entity
}*/
