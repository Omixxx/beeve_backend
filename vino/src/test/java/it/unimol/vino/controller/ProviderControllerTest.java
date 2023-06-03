package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.models.request.UpdateProviderRequest;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
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


@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProviderControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private RegisterProviderRequest registerProviderRequest;
    private UpdateProviderRequest updateProviderRequest;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private UserRepository userRepository;
    private AuthToken tokenClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void postProviderRegisterTestBad() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Roberto", "Telefono", "email", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void postProviderRegisterTestBad2() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Roberto", "Telefono", "a.c@.n", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void postProviderRegisterTestBad3() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Roberto", "234343424", "email", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postProviderRegisterTestBad1() throws Exception {
        registerProviderRequest = new RegisterProviderRequest(" ", "23453456", "a.c@a.i", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postProviderRegisterTestOk() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Giovanni", "23453456", "a.c@a.i", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void postProviderUpdateTestOk() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("MAria", "23453456", "a.c@a.i", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                .header("Authorization", "Bearer " + tokenClass.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerProviderRequest)));

        updateProviderRequest = new UpdateProviderRequest("MAria", "Giacomo", "2345433424", "a.c@a.i");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/updateProvider")
                        .header("Authorization", "Bearer" + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isOk());
    }
    @Test(expected = AssertionError.class)
            public void doublepost() throws Exception {
        postProviderRegisterTestOk();
        postProviderRegisterTestOk();
    }
    @Test
    public void postProviderUpdateTestBad() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Marzo", "23453456", "a.c@a.i", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                .header("Authorization", "Bearer " + tokenClass.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerProviderRequest)));

        updateProviderRequest = new UpdateProviderRequest("Marzo", " ", "2345433424", "a.c@a.i");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/updateProvider")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isBadRequest());
    }
@Test
    public void postProviderUpdateTestBad1() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("MAggio", "23453456", "a.c@a.i", "andress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/updateProvider")
                .header("Authorization", "Bearer " + tokenClass.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerProviderRequest)));

        updateProviderRequest = new UpdateProviderRequest("Maggio", "Giacomo", " ", "a.c@a.i");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/updateProvider")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isBadRequest());
    }
@Test
    public void deleteProviderTestOK() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Giovanni", "23453456", "a.c@a.i", "anddress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                .header("Authorization", "Bearer " + tokenClass.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerProviderRequest)));

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/provider/Giovanni")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isOk());
    }
@Test
    public void deleteProviderTestBad() throws Exception {
        tokenClass = new AuthToken(sectorRepository,userRepository);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/provider/gdfgdfdf")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getProviderTest() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Maria", "25453456", "a.b@a.i", "cnddress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerProviderRequest)));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/provider")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].*").exists());

    }@Test
    public void getProviderNameTest() throws Exception {
        registerProviderRequest = new RegisterProviderRequest("Maria", "25453456", "a.b@a.i", "cnddress");
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/provider/register")
                .header("Authorization", "Bearer " + tokenClass.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerProviderRequest)));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/provider/Maria")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Maria"))
                .andExpect(jsonPath("$.phone_number").value("25453456"))
                .andExpect(jsonPath("$.email").value("a.b@a.i"))
                .andExpect(jsonPath("$.address").value("cnddress"));

    }


}
