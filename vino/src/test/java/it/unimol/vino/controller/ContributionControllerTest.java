package it.unimol.vino.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.utils.AuthToken;
import it.unimol.vino.models.request.RegisterContributionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ContributionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    private AuthToken tokenClass;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenClass= new AuthToken();


    }
    /*@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest)
            throws PasswordNotValidException, UserAlreadyRegistered {
        return ResponseEntity.ok(this.authService.register(registerRequest));
    }
*/


    @Test
    public void putContributionTest() throws Exception {
        RegisterContributionRequest contribution = new RegisterContributionRequest();
        // Imposta i campi dell'oggetto contribution
        contribution.setOrigin("...");
        contribution.setCountry("...");
        contribution.setPhotoURL("...");
        contribution.setDescription("...");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setGrapeTypeId("...");
        contribution.setProviderId(123L);
        objectMapper=new ObjectMapper();
        tokenClass =new AuthToken();

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/contribution")
                        .header("Authorization", "Bearer " +tokenClass.generateToken() )
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contribution)))
                .andExpect(status().isOk());
    }
}

