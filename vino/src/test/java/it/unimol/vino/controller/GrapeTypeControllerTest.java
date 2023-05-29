package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.models.entity.GrapeType;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GrapeTypeControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private GrapeType grapeType;
    @Autowired
    private UserRepository userRepository;
    private AuthToken tokenClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void putGrapeTestOk() throws Exception {
        grapeType = GrapeType.builder().color("nero").species("Monaco").id(1L).build();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grapeType)))
                .andExpect(status().isOk());
    }

    @Test
    public void putGrapeTestOK1() throws Exception {
        grapeType = GrapeType.builder().color("nero").species("Monaco").id(1L).build();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grapeType)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("species").value("MONACO"))
                .andExpect(jsonPath("color").value("NERO"));
    }

    @Test(expected = ServletException.class)
    public void putGrapeTestBad() throws Exception {
        grapeType = GrapeType.builder().color(" ").species("Monaco").id(1L).build();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grapeType)))
                .andExpect(status().isBadRequest());
    }

    @Test(expected = ServletException.class)
    public void putGrapeTestBad1() throws Exception {
        grapeType = GrapeType.builder().color("Nero").species(" ").id(1L).build();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grapeType)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getGrapeTestOk() throws Exception {
        putGrapeTestOk();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].species").value("Monaco"))
                .andExpect(jsonPath("$[0].color").value("nero"));
    }

    @Test
    public void getAllBySpecies() throws Exception {
        putGrapeTestOk();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/grape-type/Monaco")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].species").value("Monaco"))
                .andExpect(jsonPath("$[0].color").value("nero"));
    }
}
