package it.unimol.vino.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.dto.SectorDTO;
import it.unimol.vino.dto.SectorPermissionDTO;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import jakarta.servlet.ServletException;
import lombok.*;
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

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("h2")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private UpdatePermissionsRequest updatePermissionsRequest;
    @Autowired
    private UserRepository userRepository;
    private AuthToken tokenClass;
    @Autowired
    private SectorRepository sectorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void postCategoryTestOk() throws Exception {
        List<UserSectorPermission> list = new ArrayList<UserSectorPermission>();
        UserSectorPermission userSectorPermission = new UserSectorPermission();
        User user = new User();
        user.setRole(Role.USER);
        user.setFirstName("C");
        user.setLastName("H");
        user.setEmail("a@b.com");
        user.setPassword("Abcd9876");
        userSectorPermission.setUser(user);
        list.add(userSectorPermission);
        user.setPermissions(list);
        List<Sector> sectors = this.sectorRepository.findAll();
        sectors.forEach(user::addPermission);
        userRepository.save(user);


        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);

        UpdatePermissionsRequestDTO updatePermissionsRequestDTO = new UpdatePermissionsRequestDTO();
        updatePermissionsRequestDTO.setEmail("a@b.com");

        HashMap<SectorName, UserSectorPermissionDTO> permissions = new HashMap<>();
        UserSectorPermissionDTO userSectorPermissionDTO = new UserSectorPermissionDTO();
        userSectorPermissionDTO.setCanRead(false);
        userSectorPermissionDTO.setCanWrite(false);
        userSectorPermissionDTO.setCanUpdate(false);
        userSectorPermissionDTO.setCanDelete(false);
        userSectorPermissionDTO.setSector(null);

        permissions.put(SectorName.CONFERIMENTO, userSectorPermissionDTO);

        updatePermissionsRequestDTO.setPermissions(permissions);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/user/update_permissions")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePermissionsRequestDTO)))
                .andExpect(status().isOk());
    }

}

@Data
@Setter
@Getter
@NoArgsConstructor
class UpdatePermissionsRequestDTO {
    private String email;
    private HashMap<SectorName, UserSectorPermissionDTO> permissions;

}

@NoArgsConstructor
@Setter
@Getter
@Data
class UserSectorPermissionDTO {
    private Long id;
    private Sector sector;
    private Boolean canRead;
    private Boolean canWrite;
    private Boolean canUpdate;
    private Boolean canDelete;


}

