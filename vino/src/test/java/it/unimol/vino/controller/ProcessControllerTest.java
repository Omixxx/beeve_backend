package it.unimol.vino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.*;

import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.models.request.ProgressProcessRequest;
import it.unimol.vino.repository.*;
import it.unimol.vino.utils.AuthToken;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.stream.Stream;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProcessControllerTest {
    @PersistenceContext
    private EntityManager entityManager;
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
    private State state1;
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
    @Transactional
    private void setupdb() {
        tokenClass = new AuthToken(sectorRepository, userRepository);
        if (stateRepository.findAll().isEmpty()) {
            state = State.builder().name("progresso").doesProduceWaste(false).build();
            stateRepository.save(state);
            state1 = State.builder().name("pigiatura").doesProduceWaste(false).build();
            stateRepository.save(state1);}
            if(stateRepository.findAll().size()<2){
            state1 = State.builder().name("pigiatura").doesProduceWaste(false).build();
            stateRepository.save(state1);}
            if(itemRepository.findAll().isEmpty()){
            Category category = Category.builder().name("GIACOMO").isPrimary(true).build();
            System.out.println(category);
            categoryRepository.save(category);
            item = Item.builder().name("bottiglieVerdi").totQuantity(500).category(categoryRepository.findByName("GIACOMO").get()).build();
            itemRepository.save(item);
            }
            if(contributionRepository.findAll().isEmpty()){
            GrapeType grapeType = GrapeType.builder().id(1L).species("Roberto").color("nera").build();
            grapeTypeRepository.save(grapeType);
            Provider provider = Provider.builder().id(1L).email("a.a@c").build();
            providerRepository.save(provider);
            contribution = Contribution.builder().origin("italia").date(new Date()).country("Campania")
                    .associatedGrapeType(grapeType).provider(provider).sugarDegree(30.0).quantity(10.0).
                    submitter(tokenClass.getUser()).build();
            contributionRepository.save(contribution);
        }
            state1=stateRepository.findAll().get(1);
            state=stateRepository.findAll().get(0);
            item=itemRepository.findAll().get(0);
            contribution=contributionRepository.findAll().get(0);

    }

    private Long createNewProcess(NewProcessRequest request) {
        setupdb();
        List<State> alreadyOrderedStateList = new ArrayList<>();

        State finalState = this.stateRepository.findByName("Completato").orElseThrow(
                () -> new InternalServerErrorException("Errore Interno, contattare l'amministratore")
        );
        if (!request.getStates().contains(finalState.getId()))
            request.getStates().add(finalState.getId());

        request.getStates().forEach((stateId) -> {
            State state = this.stateRepository.findById(stateId).orElseThrow(
                    () -> new StateNotFoundException("Stato con id " + stateId + " non trovato")
            );

            alreadyOrderedStateList.add(state);
        });

        HashMap<Item, Integer> itemQuantityMap = new HashMap<>();
        request.getItemIdUsedQuantity().forEach((itemId, quantity) -> {
            Item item = this.itemRepository.findById(itemId).orElseThrow(
                    () -> new ItemNotFoundException("Item con id " + itemId + " non trovato")
            );
            Integer totalQuantity = item.getTotQuantity();
            if (totalQuantity < quantity)
                throw new QuantityNotAvailableException("Quantità non sufficiente per l'item " + item.getDescription() +
                        " richiesta: " + quantity + " disponibile: " + totalQuantity);
            item.setTotQuantity(totalQuantity - quantity);
            itemQuantityMap.put(item, quantity);
        });

        HashMap<Contribution, Double> contributionQuantityMap = new HashMap<>();
        request.getContributionIdQuantity().forEach((contributionId, quantity) -> {
            Contribution contribution = this.contributionRepository.findById(contributionId).orElseThrow(
                    () -> new ContributionNotFoundException("Conferimento con id " + contributionId + " non trovato")
            );
            Double totalQuantity = contribution.getQuantity();
            if (totalQuantity < quantity)
                throw new QuantityNotAvailableException("Quantità non sufficiente per il conferimento "
                        + contribution.getId() + " richiesta: " + quantity + " disponibile: " + totalQuantity);
            contribution.setQuantity(totalQuantity - quantity);
            contributionQuantityMap.put(contribution, quantity);
        });

        Process process = new Process(alreadyOrderedStateList, itemQuantityMap, contributionQuantityMap);
        User user = this.getUser();
        process.setCreator(user);

        return this.processRepository.save(process).getId();
    }
    private User getUser() {
        User user;
        if(userRepository.findAll().isEmpty()){
            Stream.of(SectorName.values()).forEach(sectorName -> {
                Sector permission = new Sector(sectorName);
                if (this.sectorRepository.findSectorBySectorName(permission.getSectorName()).isEmpty())
                    this.sectorRepository.save(permission);
            });
            List<UserSectorPermission> list = new ArrayList<UserSectorPermission>();
            UserSectorPermission userSectorPermission = new UserSectorPermission();
            user = new User();
            user.setRole(Role.ADMIN);
            user.setFirstName("A");
            user.setLastName("B");
            user.setEmail("a@a.com");
            user.setPassword("Abcd9876");
            userSectorPermission.setUser(user);
            list.add(userSectorPermission);
            user.setPermissions(list);
            List<Sector> sectors = this.sectorRepository.findAll();
            sectors.forEach(user::addPermission);
            userRepository.save(user);
        }
        return userRepository.findAll().get(0);
    }

    @Transactional
    private Long processdbsave(){
        setupdb();
        List<Long> list = new ArrayList<>();

        list.add(state1.getId());
        list.add(state.getId());
        HashMap<Long, Integer> itemid = new HashMap<>();
        itemid.put(item.getId(), 40);
        HashMap<Long, Double> contributionid = new HashMap<>();
        contributionid.put(contribution.getId(), 1.0);

        NewProcessRequest newProcessRequest = new NewProcessRequest(list, itemid, contributionid);
        return createNewProcess(newProcessRequest);
       /* setupdb();
        ArrayList<State> staearrey=new ArrayList<>();
        staearrey.add(stateRepository.findAll().get(0));
        HashMap<Item,Integer> itemIntegerHashMap=new HashMap<>();
        itemIntegerHashMap.put(item,1);
        HashMap<Contribution,Double> contributionDoubleHashMap=new HashMap<>();
        contributionDoubleHashMap.put(contribution,1.0);
        process=new Process(staearrey,itemIntegerHashMap,contributionDoubleHashMap);
        process.setCreator(userRepository.findAll().get(0));
        processRepository.save(process);*/
        }
        @Test
        public void postNewProcessTestOK() throws Exception {
        setupdb();
        List<Long> list = new ArrayList<>();

        list.add(state1.getId());
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

    @Test
    public void postNewProcessTestbad1() throws Exception {
        setupdb();
        List<Long> list = new ArrayList<>();

        list.add(state.getId());
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
                .andExpect(status().isConflict());
    }
    @Test
    public void postNewProcessTestbad2() throws Exception {
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
                .andExpect(status().isBadRequest());
    }


    @Test(expected = AssertionError.class)
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

   /* @Test
    public void ProgressRequestTest()throws Exception{
        Long id=processdbsave();
        objectMapper = new ObjectMapper();

        ProgressProcessRequest processRequest=new ProgressProcessRequest();
        processRequest.setWaste(10);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/process/"+id+"/progress")
                .header("Authorization", "Bearer" + tokenClass.generateToken())
            .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(processRequest)))
            .andExpect(status().isOk());
}*/
}



