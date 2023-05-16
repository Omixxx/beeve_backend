package it.unimol.vino.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.repository.GrapeTypeRepository;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import it.unimol.vino.models.request.RegisterContributionRequest;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.constraints.Null;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ContributionControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GrapeTypeRepository grapeTypeRepository;


    @Mock
    private AuthToken tokenClass;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /*@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest)
            throws PasswordNotValidException, UserAlreadyRegistered {
        return ResponseEntity.ok(this.authService.register(registerRequest));
    }
*/


    @Test
    @Transactional
    public void putContributionTest() throws Exception {
        Provider provider = new Provider();
        provider.setEmail("a.a@c");
        providerRepository.save(provider);
        RegisterContributionRequest contribution = new RegisterContributionRequest();
        GrapeType grapeType=GrapeType.builder().type("Salvatore").species("Nera").color("nera").build();
        when(grapeTypeRepository.findById("Salvatore")).thenReturn(Optional.of(grapeType));
        contribution.setOrigin("Italia");
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setGrapeTypeId("Salvatore");
        contribution.setProviderId(provider.getId());
        objectMapper=new ObjectMapper();
        tokenClass =new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/contribution")
                        .header("Authorization", "Bearer " +tokenClass.generateToken() )
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contribution)))
                .andExpect(status().isOk());
    }
}

