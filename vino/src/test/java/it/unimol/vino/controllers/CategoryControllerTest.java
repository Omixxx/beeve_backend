package it.unimol.vino.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;


import org.junit.Before;
import org.junit.Test;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("h2")
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private CategoryRequest categoryRequest;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private UserRepository userRepository;
    private AuthToken tokenClass;

    @Before
   public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void postCategoryTestOk() throws Exception {
        categoryRequest = new CategoryRequest("Giacomo", true);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/category")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void postCategoryTestFail() throws Exception {
        categoryRequest = new CategoryRequest(" ", false);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/category")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void double_post() throws Exception {
        postCategory("MARCO", true);
        categoryRequest = new CategoryRequest("marco", false);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/category")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isConflict());

    }


    @Test
    public void getAllCategory() throws Exception {
        postCategory("Giovanna", true);
        tokenClass = new AuthToken(sectorRepository, userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].*").exists());

    }

    @Test
    public void postCategoryTestbed() throws Exception {
        categoryRequest = new CategoryRequest(" ", true);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/category")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest());
    }

    public void postCategory(String name, boolean isPrimary) throws Exception {
        categoryRequest = new CategoryRequest(name, isPrimary);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/category")
                .header("Authorization", "Bearer " + tokenClass.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)));

    }

}
