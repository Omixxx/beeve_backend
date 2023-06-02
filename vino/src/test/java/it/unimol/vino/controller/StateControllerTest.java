package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import jakarta.servlet.ServletException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

public class StateControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private NewStateRequest newStateRequest;
    private AuthToken tokenClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void postNewStateTestOk() throws Exception {
        newStateRequest= new NewStateRequest("Giovanna", true);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/state")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStateRequest)))
                .andExpect(status().isOk());
    } @Test
    public void postNewStateTestBad() throws Exception {
        newStateRequest= new NewStateRequest(" ", true);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/state")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStateRequest)))
                .andExpect(status().isBadRequest());
    }@Test
    public void postNewStateTestBad1() throws Exception {
        newStateRequest= new NewStateRequest();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/state")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStateRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void getNewStateTest() throws Exception {
        newStateRequest= new NewStateRequest("Giovanni", true);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/state")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStateRequest)))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/state")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Giovanni"))
                .andExpect(jsonPath("$[0].doesProduceWaste").value(true));
    }


}
