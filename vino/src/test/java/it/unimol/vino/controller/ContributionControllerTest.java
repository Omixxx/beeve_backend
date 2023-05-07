package it.unimol.vino.controller;

import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.services.ContributionService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*public class ContributionControllerTest {
    @MockBean
    private ContributionRepository contributionRepository;
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ContributionService contributionService;

    @Test
    public void getAll_ShouldReturnAllContributions() throws Exception {
        contributionService=new ContributionService(contributionRepository);
        GrapeType grapeType1=new GrapeType();
        grapeType1.setId("Sangiovese");
        grapeType1.setSpecies("Sangiovese");
        Contribution contribution1 = new Contribution(1L, "Italy", "Chianti", "http://example.com/photo1", "description1", 10.0, 100.0, grapeType1);
        Contribution contribution2 = new Contribution(2L, "France", "Bordeaux", "http://example.com/photo2", "description2", 15.0, 200.0, grapeType1);
        List<Contribution> contributions = Arrays.asList(contribution1, contribution2);

        given(contributionService.getAll()).willReturn(contributions);

        mockMvc.perform(get("/api/v1/contribution"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].origin", is("Italy")))
                .andExpect(jsonPath("$[0].country", is("Chianti")))
                .andExpect(jsonPath("$[0].photoURL", is("http://example.com/photo1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[0].sugarDegree", is(10.0)))
                .andExpect(jsonPath("$[0].quantity", is(100.0)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].origin", is("France")))
                .andExpect(jsonPath("$[1].country", is("Bordeaux")))
                .andExpect(jsonPath("$[1].photoURL", is("http://example.com/photo2")))
                .andExpect(jsonPath("$[1].description", is("description2")))
                .andExpect(jsonPath("$[1].sugarDegree", is(15.0)))
                .andExpect(jsonPath("$[1].quantity", is(200.0)));
    }

}*/
