package it.unimol.vino.controllers;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mariadb.jdbc.client.tls.HostnameVerifier.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testConstructorAndGetters() {
        @Test
        public void testUpdatePermissionsRequest() {
            // crea un'istanza di UserSectorPermission con i permessi desiderati
            UserSectorPermission permission = UserSectorPermission.builder()
                    .canCreate(true)
                    .canDelete(false)
                    .canUpdate(true)
                    .canRead(true)
                    .build();

            // costruisce una mappa di permessi usando SectorName come chiave
            Map<SectorName, UserSectorPermission> permissionsMap = new HashMap<>();
            permissionsMap.put(SectorName.SALES, permission);
            permissionsMap.put(SectorName.MARKETING, permission);

            // crea un'istanza di UpdatePermissionsRequest con la mappa di permessi appena costruita
            UpdatePermissionsRequest updatePermissionsRequest = new UpdatePermissionsRequest();
            updatePermissionsRequest.setPermissions(permissionsMap);

            // verifica che la mappa di permessi sia stata impostata correttamente
            assertEquals(permissionsMap, updatePermissionsRequest.getPermissions());
        }








        @Test
    public void testUpdatePermissions_Success() throws Exception {
        UpdatePermissionsRequest request = new UpdatePermissionsRequest();
        request.setUsername("test_user");
        request.setSectors(Arrays.asList("sector1", "sector2"));

        UpdatePermissionResponse response = new UpdatePermissionResponse("Permissions updated successfully");

        when(userService.updatePermissions(any(UpdatePermissionsRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/update_permissions")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testUpdatePermissions_Failure() throws Exception {
        UpdatePermissionsRequest request = new UpdatePermissionsRequest();
        request.setUsername("test_user");
        request.setSectors(Collections.singletonList(""));

        UpdatePermissionResponse response = new UpdatePermissionResponse("Invalid sector name");

        when(userService.updatePermissions(any(UpdatePermissionsRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/update_permissions")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }
}

