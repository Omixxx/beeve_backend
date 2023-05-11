/*package it.unimol.vino.service;

import it.unimol.vino.exceptions.ContributionNotFoundException;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.ContributionRepository;
import it.unimol.vino.services.ContributionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ContributionServiceTest {

    @MockBean
    private ContributionRepository contributionRepository;

    private ContributionService contributionService;
    private Contribution contribution;
    private GrapeType grapeType;

    @BeforeEach
    void setUp() {
        contributionService = new ContributionService(contributionRepository);
        contribution= new Contribution();
        grapeType=new GrapeType();
        grapeType.setSpecies("nuova");
        grapeType.setId("uvaNuova");
        grapeType.setColor("nera");
        contribution.setQuantity(100);
        contribution.setDescription("CI È STATA DATA DA GIUSEPPE");
        contribution.setOrigin("Marche");
        contribution.setSugarDegree(1000);
        contribution.setAssociatedGrapeType(grapeType);
        contribution.setCountry("Spain");

    }

    @Test
    void testGetAll() {
        List<Contribution> expectedContributionList = new ArrayList<>();
        Contribution contribution1 = new Contribution();
        Contribution contribution2 = new Contribution();
        expectedContributionList.add(contribution1);
        expectedContributionList.add(contribution2);

        when(contributionRepository.findAll()).thenReturn(expectedContributionList);

        List<Contribution> actualContributionList = contributionService.getAll();

        assertEquals(expectedContributionList, actualContributionList);
    }

    @Test
    void testGet() {
        Long expectedId = 1L;
        Contribution expectedContribution = new Contribution();
        expectedContribution.setId(expectedId);

        when(contributionRepository.findById(expectedId)).thenReturn(Optional.of(expectedContribution));

        Contribution actualContribution = contributionService.get(expectedId);

        assertEquals(expectedContribution, actualContribution);
    }

    @Test
    void testGetWithNonexistentId() {
        Long nonexistentId = 1L;

        when(contributionRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        ContributionNotFoundException exception = assertThrows(ContributionNotFoundException.class, () -> {
            contributionService.get(nonexistentId);
        });

        String expectedMessage = "Il conferimento con id " + nonexistentId + " non esiste";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetByOrigin() {
        String expectedOrigin = "expectedOrigin";
        List<Contribution> expectedContributionList = new ArrayList<>();
        Contribution contribution1 = new Contribution();
        Contribution contribution2 = new Contribution();
        expectedContributionList.add(contribution1);
        expectedContributionList.add(contribution2);

        when(contributionRepository.findByOrigin(expectedOrigin)).thenReturn(Optional.of(expectedContributionList));

        List<Contribution> actualContributionList = contributionService.getByOrigin(expectedOrigin);

        assertEquals(expectedContributionList, actualContributionList);
    }

    @Test
    void testGetByOriginWithNonexistentOrigin() {
        String nonexistentOrigin = "nonexistentOrigin";

        when(contributionRepository.findByOrigin(nonexistentOrigin)).thenReturn(Optional.empty());

        ContributionNotFoundException exception = assertThrows(ContributionNotFoundException.class, () -> {
            contributionService.getByOrigin(nonexistentOrigin);
        });

        String expectedMessage = "Non esiste alcun conferimento con origine " + nonexistentOrigin;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }*/
//TODO casi di test che lanciano eccezzioni che non dovrebbero
  /*  @Test
    void testGetByCountry() {
        List<Contribution> contributions = contributionService.getByCountry("Spain");
        assertNotNull(contributions);
        assertEquals(contributions.size(), 1);
        assertEquals(contributions.get(0).getSugarDegree(), 13.0);
    }

    @Test
    void testGetBySugarDegree() {
        List<Contribution> contributions = contributionService.getBySugarDegree(1000);
        assertNotNull(contributions);
        assertEquals(contributions.size(), 2);
        assertEquals(contributions.get(0).getQuantity(), 100.0);
    }

    @Test
    void testGetByQuantity() {
        List<Contribution> contributions = contributionService.getByQuantity(500.0);
        assertNotNull(contributions);
        assertEquals(contributions.size(), 1);
        assertEquals(contributions.get(0).getDescription(), "This is a contribution from a small vineyard in Italy.");
    }

    @Test
    void testGetByAssociatedGrapeType() {
        List<Contribution> contributions = contributionService.getByAssociatedGrapeType(new GrapeType());
        assertNotNull(contributions);
        assertEquals(contributions.size(), 1);
        assertEquals(contributions.get(0).getPhotoURL(), "https://example.com/cabernet-sauvignon.jpg");
    }

    @Test
    void testPut() {
        Contribution contribution = Contribution.builder()
                .origin("New Zealand")
                .associatedGrapeType(new GrapeType())
                .description("This is a new contribution.")
                .sugarDegree(12.0)
                .quantity(250.0)
                .photoURL("https://example.com/new-contribution.jpg")
                .build();
        Contribution savedContribution = contributionService.put(contribution);
        assertNotNull(savedContribution);
        assertNotNull(savedContribution.getId());
    }

    @Test
    void testReplace() {
        Contribution contribution = Contribution.builder()
                .origin("New Zealand")
                .associatedGrapeType(new GrapeType())
                .description("This is a replacement contribution.")
                .sugarDegree(12.5)
                .quantity(350.0)
                .photoURL("https://example.com/replacement-contribution.jpg")
                .build();
        Contribution replacedContribution = contributionService.replace(1L, contribution);
        assertNotNull(replacedContribution);
        assertEquals(replacedContribution.getCountry(), "Spain");
        assertEquals(replacedContribution.getDescription(), "This is a replacement contribution.");
    }

    @Test
    void testUpdateOrigin() {
        contributionService.updateOrigin(1L, "Argentina");
        Contribution contribution = contributionService.get(1L);
        assertNotNull(contribution);
        assertEquals(contribution.getOrigin(), "Argentina");
    }

    @Test
    void testUpdateCountry() {
        contributionService.updateCountry(1L, "United States");
        Contribution contribution = contributionService.get(1L);
        assertNotNull(contribution);
        assertEquals(contribution.getCountry(), "United States");
    }

    @Test
    void testUpdateSugarDegree() {
        contributionService.updateSugarDegree(1L, 12.5);
        Contribution contribution = contributionService.get(1L);
        assertNotNull(contribution);
        assertEquals(contribution.getSugarDegree(), 12.5);
    }

}*/