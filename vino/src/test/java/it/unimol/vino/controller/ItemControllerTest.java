package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.repository.ProviderRepository;
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


import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ItemControllerTest {
@Autowired
private  CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProviderRepository providerRepository;
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
    public void putItemTestOk() throws Exception {

        RegisterItemRequest registerItemRequest = RegisterItemRequest.builder()
                .capacity(0.75f)
                .description("Descrizione dell'elemento")
                .provider_id(providerId())
                .date(new Date())
                .quantity(10)
                .categoryName(categoryName())
                .name("Nome elemento")
                .build();

        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/item/")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerItemRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void putItemTestOk1() throws Exception {
        categoryName();
        RegisterItemRequest registerItemRequest = RegisterItemRequest.builder()
                    .capacity(0.75f)
                    .description("Descrizione dell'elemento")
                    .provider_id(providerId())
                    .date(new Date())
                    .quantity(10)
                    .categoryName("vino")
                    .name("Nome elemento")
                    .build();

            objectMapper = new ObjectMapper();
            tokenClass = new AuthToken(userRepository);

            mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/item/")
                            .header("Authorization", "Bearer " + tokenClass.generateToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerItemRequest)))
                    .andExpect(status().isOk());
    }

    @Test(expected = NullPointerException.class)
    public void putItemTestBead() throws Exception {
        categoryName();
        RegisterItemRequest registerItemRequest = RegisterItemRequest.builder()
                .capacity(0.75f)
                .description("Descrizione dell'elemento")
                .provider_id(null)
                .date(new Date())
                .quantity(10)
                .categoryName("vino")
                .name("Nome elemento")
                .build();

        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/item/")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerItemRequest)))
                .andExpect(status().isBadRequest());
    }



    public long providerId() {
        Provider provider= Provider.builder().id(1L).email("a.a@c").build();
        if(providerRepository.findById(1L).isEmpty())
            providerRepository.save(provider);
        return 1L;
    }
    public String categoryName() {
       Category category= Category.builder().name("VINO").isPrimary(false).build();
        if(categoryRepository.findByName("VINO").isEmpty())
            categoryRepository.save(category);
        return "VINO";
    }


}
