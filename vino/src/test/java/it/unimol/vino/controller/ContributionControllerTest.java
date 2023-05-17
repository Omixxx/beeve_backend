package it.unimol.vino.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.repository.GrapeTypeRepository;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import it.unimol.vino.models.request.RegisterContributionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;


import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;




import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ContributionControllerTest {
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GrapeTypeRepository grapeTypeRepository;


    @Mock
    private AuthToken tokenClass;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void putContributionTest() throws Exception {
        Provider provider = new Provider();
        provider.setEmail("a.a@c");
        providerRepository.save(provider);
        RegisterContributionRequest contribution = new RegisterContributionRequest();
        GrapeType grapeType = GrapeType.builder().type("Salvatore").species("Nera").color("nera").build();
        grapeTypeRepository.save(grapeType);
        contribution.setOrigin("Italia");
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setGrapeTypeId("Salvatore");
        contribution.setProviderId(provider.getId());
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/contribution")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contribution)))
                .andExpect(status().isOk());
    }
    @Test
    public void putContributionTestBadRequest() throws Exception {
        Provider provider = new Provider();
        provider.setEmail("a.b@c");
        providerRepository.save(provider);
        GrapeType grapeType = GrapeType.builder().type("Salvatore").species("Bianca").color("nera").build();
        grapeTypeRepository.save(grapeType);
        RegisterContributionRequest contribution = new RegisterContributionRequest();
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setGrapeTypeId("Salvatore");
        contribution.setProviderId(provider.getId());
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/contribution")
                        .header("Authorization", "Bearer " +tokenClass.generateToken() )
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contribution)))
                .andExpect(status().isBadRequest());
    }
   @Test
    public void getAllContributionTest() throws Exception {
       Provider provider = new Provider();
       provider.setEmail("a.c@c");
       providerRepository.save(provider);
       GrapeType grapeType = GrapeType.builder().type("Salvatore").species("Bianca").color("nera").build();
       grapeTypeRepository.save(grapeType);
        tokenClass = new AuthToken(userRepository);
        Contribution contribution=new Contribution();
        contribution.setOrigin("Italia");
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
       contribution.setProvider(provider);
       contribution.setAssociatedGrapeType();
        contributionRepository.save(contribution);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contribution")
                    .header("Authorization", "Bearer " + tokenClass.generateToken()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].description").value("Frizzante"));
            // Verifica ulteriori attributi o dati se necessario
        }

    }

