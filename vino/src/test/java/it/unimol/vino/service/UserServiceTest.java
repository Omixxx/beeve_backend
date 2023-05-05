/*package it.unimol.vino.service;

import it.unimol.vino.exceptions.SectorNotFoundException;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;

import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;

import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SectorRepository sectorRepository;

    private UserService userService;

    private User testUser;

    private final String testUserEmail = "test@example.com";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        userService = new UserService(userRepository, sectorRepository);

        testUser = new User(1L,"name","surname",testUserEmail,"password",);
    }

    @Test
    public void testUpdatePermissions() {


        when(userRepository.findByEmail(testUserEmail)).thenReturn(Optional.of(testUser));

        UpdatePermissionsRequest updatePermissionsRequest = new UpdatePermissionsRequest();
        updatePermissionsRequest.setPermissions(true,true,true);

        UpdatePermissionResponse response = userService.updatePermissions(updatePermissionsRequest);

        verify(userRepository).save(testUser);
        assertTrue(testUser.getPermissions().stream()
                .anyMatch(p -> p.getPermissionTypes().containsAll(testPermissionTypes) && p.getSector().equals(testSector)));

        assertEquals("Permessi aggiornati con successo!", response.getMessage());
    }

}*/

//TODO da capire come funzionano i permessi



