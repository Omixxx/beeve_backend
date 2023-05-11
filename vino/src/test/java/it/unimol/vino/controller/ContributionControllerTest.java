package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.controllers.ContributionController;
import it.unimol.vino.models.request.RegisterContributionRequest;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.services.ContributionService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ContributionControllerTest {
    @MockBean
    private ContributionRepository contributionRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ContributionService contributionService;
    @Autowired
    private ContributionController contributionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contributionController = new ContributionController(contributionService);
    }

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

        mockMvc.perform(post("http://localhost:8080/api/v1/contribution")
                        .contentType("application/json")
                        .content(objectMapper.))
                .andExpect(status().isOk());
    }
}

