package it.unimol.vino.controller;

import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc

public class SectorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private AuthToken tokenClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void getSectorTest() throws Exception {
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/Sector")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("Conferimento"))
                .andExpect(jsonPath("$[1].name").value("Fornitura"))
                .andExpect(jsonPath("$[2].name").value("Magazzino"))
                .andExpect(jsonPath("$[3].name").value("Processo"));

    }
}
