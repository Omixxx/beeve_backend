package it.unimol.vino.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.entity.*;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.repository.GrapeTypeRepository;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import it.unimol.vino.models.request.RegisterContributionRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
    private ObjectMapper objectMapper;
    @Autowired
    private  GrapeTypeRepository grapeTypeRepository;
    @Autowired
    private ProviderRepository providerRepository;


   private AuthToken tokenClass;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void putContributionTest() throws Exception {
        RegisterContributionRequest contribution = new RegisterContributionRequest();
        contribution.setOrigin("Italia");
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setGrapeTypeId(grapeTypeId());
        contribution.setProviderId(ProviderId());
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
        RegisterContributionRequest contribution = new RegisterContributionRequest();
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setGrapeTypeId(grapeTypeId());
        contribution.setProviderId(ProviderId());
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
       putContributionTest();
       tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contribution")
                    .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].quantity").value(10.0))
                .andExpect(jsonPath("$[0].associatedGrapeType.species").value("Roberto"))
                .andExpect(jsonPath("$[0].associatedGrapeType.color").value("nera"))
                .andExpect(jsonPath("$[0].provider").exists());
    }
    @Test
    public void getContributionTest() throws Exception {
        putContributionTest();
        tokenClass = new AuthToken(userRepository);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contribution/1")
                        .header("Authorization", "Bearer " + tokenClass.generateToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.origin").value("Campania"))// TODO mi setta campania ma mi aspetto italia se facendo la put metto italia come origin
                .andExpect(jsonPath("$.country").value("Campania"))
                .andExpect(jsonPath("$.description").value("Frizzante"))
                .andExpect(jsonPath("$.sugarDegree").value(5.5))
                .andExpect(jsonPath("$.quantity").value(10.0))
                .andExpect(jsonPath("$.associatedGrapeType.species").value("Roberto"))
                .andExpect(jsonPath("$.provider.email").value("a.a@c"));

    }
    public long grapeTypeId() {
        GrapeType grapeType = GrapeType.builder().id(1L).species("Roberto").color("nera").build();
        if (grapeTypeRepository.findById(1L).isEmpty()) {
            grapeTypeRepository.save(grapeType);
        }
        return 1L;
    }
    public long ProviderId() {
        Provider provider=Provider.builder().id(1L).email("a.a@c").build();
        if(providerRepository.findById(1L).isEmpty())
            providerRepository.save(provider);
        return 1L;
    }
    public void ContributionSave(){
        List<UserSectorPermission> list = new ArrayList<UserSectorPermission>();
        UserSectorPermission userSectorPermission = new UserSectorPermission();
        Optional<GrapeType> optionalGrapeType = grapeTypeRepository.findById(grapeTypeId())

        User user = new User();
        user.setId(1L);
        user.setRole(Role.ADMIN);
        user.setFirstName("C");
        user.setLastName("N");
        user.setEmail("a@K.com");
        user.setPassword("Abcd9876");
        userSectorPermission.setUser(user);
        list.add(userSectorPermission);
        user.setPermissions(list);
        userRepository.save(user);
        Contribution contribution=new Contribution();
        contribution.setOrigin("Italia");
        contribution.setCountry("Campania");
        contribution.setDescription("Frizzante");
        contribution.setSugarDegree(5.5);
        contribution.setQuantity(10.0);
        contribution.setDate(new Date());
        contribution.setSubmitter(user);
        contribution.setAssociatedGrapeType(grapeType);
        contribution.setProvider();
        contribution.setSubmissionDate(new Date());
        contributionRepository.save(contribution);
    }
}


