package it.unimol.vino.entity;
import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContributionEntityTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        String origin = "Italia";
        String country = "Italia";
        String photoURL = "http://example.com/photo.jpg";
        String description = "Questo è un contributo";
        double sugarDegree = 12.3;
        double quantity = 45.6;
        GrapeType grapeType = new GrapeType();

        Contribution contribution = new Contribution(id, origin, country, photoURL, description, sugarDegree, quantity, grapeType);

        assertEquals(id, contribution.getId());
        assertEquals(origin, contribution.getOrigin());
        assertEquals(country, contribution.getCountry());
        assertEquals(photoURL, contribution.getPhotoURL());
        assertEquals(description, contribution.getDescription());
        assertEquals(sugarDegree, contribution.getSugarDegree());
        assertEquals(quantity, contribution.getQuantity());
        assertEquals(grapeType, contribution.getAssociatedGrapeType());
    }
}
