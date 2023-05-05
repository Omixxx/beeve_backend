package it.unimol.vino.entity;
import it.unimol.vino.models.entity.GrapeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
//mi fa creare comunque classi con valori non validi
public class GrapeTypeEntityTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testEmptyGrapeTypeIsInvalid() {
        GrapeType grapeType = GrapeType.builder().build();

        Set<ConstraintViolation<GrapeType>> violations = validator.validate(grapeType);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Il nome del vitigno è obbligatorio", violations.iterator().next().getMessage());
    }

    @Test
    public void testGrapeTypeWithNameIsValid() {
        GrapeType grapeType = GrapeType.builder().id("Sangiovese").build();

        Set<ConstraintViolation<GrapeType>> violations = validator.validate(grapeType);
        Assertions.assertEquals(0, violations.size());
    }
}
