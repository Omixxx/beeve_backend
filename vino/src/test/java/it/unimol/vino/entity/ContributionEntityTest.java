package it.unimol.vino.entity;

import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
// TODO  mi fa creare classi con valori non validi
public class ContributionEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

   /*@Test
    public void testContributionWithNegativeQuantityIsInvalid() {
        Contribution contribution = Contribution.builder()
                .origin("Italy")
                .country("Italy")
                .photoURL("http://example.com/image.jpg")
                .description("A great contribution")
                .sugarDegree(10.0)
                .quantity(-100.0)
                .associatedGrapeType(new GrapeType())
                .build();

        Set<ConstraintViolation<Contribution>> violations = validator.validate(contribution);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Il contributo deve avere una quantità positiva", violations.iterator().next().getMessage());
    }

   @Test
    public void testContributionWithNullGrapeTypeIsInvalid() {
        Contribution contribution = Contribution.builder()
                .origin("Italy")
                .country("Italy")
                .photoURL("http://example.com/image.jpg")
                .description("A great contribution")
                .sugarDegree(10.0)
                .quantity(100.0)
                .associatedGrapeType(null)
                .build();

        Set<ConstraintViolation<Contribution>> violations = validator.validate(contribution);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Non può essere nullo", violations.iterator().next().getMessage());
    }

    @Test
    public void testContributionWithBlankOriginIsInvalid() {
        Contribution contribution = Contribution.builder()
                .origin("")
                .country("Italy")
                .photoURL("http://example.com/image.jpg")
                .description("A great contribution")
                .sugarDegree(10.0)
                .quantity(100.0)
                .associatedGrapeType(new GrapeType())
                .build();

        Set<ConstraintViolation<Contribution>> violations = validator.validate(contribution);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Il contributo deve avere un' origine", violations.iterator().next().getMessage());
    }

    @Test
    public void testContributionWithBlankCountryIsInvalid() {
        Contribution contribution = Contribution.builder()
                .origin("Italy")
                .country("")
                .photoURL("http://example.com/image.jpg")
                .description("A great contribution")
                .sugarDegree(10.0)
                .quantity(100.0)
                .associatedGrapeType(new GrapeType())
                .build();

        Set<ConstraintViolation<Contribution>> violations = validator.validate(contribution);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Il campo nazione non può essere vuoto", violations.iterator().next().getMessage());
    }

    @Test
    public void testContributionWithNegativeSugarDegreeIsInvalid() {
        Contribution contribution = Contribution.builder()
                .origin("Italy")
                .country("Italy")
                .photoURL("http://example.com/image.jpg")
                .description("A great contribution")
                .sugarDegree(-10.0)
                .quantity(100.0)
                .associatedGrapeType(new GrapeType())
                .build();

        Set<ConstraintViolation<Contribution>> violations = validator.validate(contribution);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Il contributo deve avere una quantità positiva", violations.iterator().next().getMessage());
    }*/
}
