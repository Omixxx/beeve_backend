package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.SectorRepository;
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
public class GrapeTypeControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private GrapeType grapeType;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SectorRepository sectorRepository;
    private AuthToken tokenClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void putGrapeTestOK1() throws Exception {
        grapeType = GrapeType.builder().color("nero").species("Monaco").id(1L).build();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);
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
        tokenClass = new AuthToken(sectorRepository, userRepository);
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
        tokenClass = new AuthToken(sectorRepository, userRepository);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grapeType)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getGrapeTestOk() throws Exception {
        putGrapeTestOk("MArrone", "Nero");
        tokenClass = new AuthToken(sectorRepository, userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].species").value("NERO"))
                .andExpect(jsonPath("$[0].color").value("MARRONE"));
    }

    @Test
    public void getAllBySpecies() throws Exception {
        putGrapeTestOk("Nero", "Rosso");
        tokenClass = new AuthToken(sectorRepository, userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/grape-type/ROSSO")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].color").value("NERO"));
    }

    public void putGrapeTestOk(String color, String species) throws Exception {
        grapeType = GrapeType.builder().color(color).species(species).id(1L).build();
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/grape-type")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grapeType)))
                .andExpect(status().isOk());
    }
}
