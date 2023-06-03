package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.entity.*;

import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.ProgressProcessRequest;
import it.unimol.vino.repository.*;
import it.unimol.vino.utils.AuthToken;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
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

import java.util.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProcessControllerTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrapeTypeRepository grapeTypeRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private ItemRepository itemRepository;
    private State state;
    private Item item;
    private Contribution contribution;
    @Autowired
    private ProcessRepository processRepository;
    private Process process;

    @Autowired
    private StateRepository stateRepository;
    private AuthToken tokenClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setupdb() {
        if (state == null) {
            tokenClass = new AuthToken(sectorRepository, userRepository);
            state = State.builder().name("progresso").doesProduceWaste(false).build();
            stateRepository.save(state);
            Category category = Category.builder().name("GIACOMO").isPrimary(true).build();
            System.out.println(category);
            categoryRepository.save(category);
            item = Item.builder().name("bottiglieVerdi").totQuantity(500).category(categoryRepository.findByName("GIACOMO").get()).build();
            itemRepository.save(item);
            GrapeType grapeType = GrapeType.builder().id(1L).species("Roberto").color("nera").build();
            grapeTypeRepository.save(grapeType);
            Provider provider = Provider.builder().id(1L).email("a.a@c").build();
            providerRepository.save(provider);
            contribution = Contribution.builder().origin("italia").date(new Date()).country("Campania")
                    .associatedGrapeType(grapeType).provider(provider).sugarDegree(30.0).quantity(10.0).
                    submitter(tokenClass.getUser()).build();
            contributionRepository.save(contribution);
        }

    }
    private void processdbsave(){
        setupdb();
        ArrayList<State> staearrey=new ArrayList<>();
        staearrey.add(stateRepository.findAll().get(0));
        HashMap<Item,Integer> itemIntegerHashMap=new HashMap<>();
        itemIntegerHashMap.put(item,1);
        HashMap<Contribution,Double> contributionDoubleHashMap=new HashMap<>();
        contributionDoubleHashMap.put(contributionRepository.findAll().get(0),1.0);
        process=new Process(staearrey,itemIntegerHashMap,contributionDoubleHashMap);
        process.setCreator(userRepository.findAll().get(0));
        processRepository.save(process);
        }


    @Test
    public void postNewProcessTestOk() throws Exception {
        setupdb();
        List<Long> list = new ArrayList<>();

        list.add(state.getId());
        HashMap<Long, Integer> itemid = new HashMap<>();
        itemid.put(item.getId(), 40);
        HashMap<Long, Double> contributionid = new HashMap<>();
        contributionid.put(contribution.getId(), 1.0);

        NewProcessRequest newProcessRequest = new NewProcessRequest(list, itemid, contributionid);
        objectMapper = new ObjectMapper();


        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/process")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProcessRequest)))
                .andExpect(status().isOk());
    }
    @Test(expected = Exception.class)
    public void postNewProcessTestbad() throws Exception {
        setupdb();
        List<Long> list = new ArrayList<>();
        list.add(state.getId());
        HashMap<Long, Integer> itemid = new HashMap<>();
        itemid.put(item.getId(), 40);
        HashMap<Long, Double> contributionid = new HashMap<>();
        contributionid.put(contribution.getId(), 0.0);

        NewProcessRequest newProcessRequest = new NewProcessRequest(list, itemid, contributionid);
        objectMapper = new ObjectMapper();


        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/process")
                        .header("Authorization", "Bearer" + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProcessRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test(expected =AssertionError.class)
    public void postNewProcessTestbad1() throws Exception {
        setupdb();
        List<Long> list = new ArrayList<>();
        list.add(state.getId());
        HashMap<Long, Integer> itemid = new HashMap<>();
        itemid.put(item.getId(), 0);
        HashMap<Long, Double> contributionid = new HashMap<>();
        contributionid.put(contribution.getId(), 5.0);

        NewProcessRequest newProcessRequest = new NewProcessRequest(list, itemid, contributionid);
        objectMapper = new ObjectMapper();


        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/process")
                        .header("Authorization", "Bearer" + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProcessRequest)))
                .andExpect(status().isBadRequest());
    }
    //non visibile dal frontend
    @Test
    public void postNewProcessTestOk2() throws Exception {
        setupdb();
        List<Long> list = new ArrayList<>();
        list.add(state.getId());
        HashMap<Long, Integer> itemid = new HashMap<>();
        itemid.put(item.getId(), 0);
        HashMap<Long, Double> contributionid = new HashMap<>();
        contributionid.put(contribution.getId(), 5.0);

        NewProcessRequest newProcessRequest = new NewProcessRequest(list, itemid, contributionid);
        objectMapper = new ObjectMapper();


        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/process")
                        .header("Authorization", "Bearer" + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProcessRequest)))
                .andExpect(status().isOk());
    }
    //TODO da finire di implementare i casi di test

    /*@Test
    public void ProgressRequestTest()throws Exception{
    processdbsave();
    Long idprocess=processRepository.findAll().get(0).getId();
    objectMapper = new ObjectMapper();

        ProgressProcessRequest processRequest=new ProgressProcessRequest();
        processRequest.setWaste(10);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/process/"+idprocess.toString()+"/progress")
                .header("Authorization", "Bearer" + tokenClass.generateToken())
            .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(processRequest)))
            .andExpect(status().isOk());
}*/
}



