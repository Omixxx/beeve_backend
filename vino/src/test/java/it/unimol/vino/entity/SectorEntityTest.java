package it.unimol.vino.entity;

import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.SectorName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class SectorEntityTest {

    private Sector sector;

    @BeforeEach
    public void setup() {
        sector = new Sector(SectorName.CONFERIMENTO);
    }

    @Test
    public void testSectorName() {
        Assertions.assertEquals(SectorName.CONFERIMENTO, sector.getSectorName());
    }

    @Test
    public void testUsersEmptyByDefault() {
        Assertions.assertNull(sector.getUsers());
    }

    @Test
    public void testUsersAddition() {
        UserSectorPermission userPermission = new UserSectorPermission();

        sector.setUsers(Collections.singletonList(userPermission));

        Assertions.assertEquals(1, sector.getUsers().size());
        Assertions.assertEquals(userPermission, sector.getUsers().get(0));
    }


}
