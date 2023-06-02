package it.unimol.vino.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.utils.AuthToken;
import jakarta.servlet.ServletException;
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
import java.util.HashMap;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void postCategoryTestOk() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        user.setFirstName("B");
        user.setLastName("C");
        user.setEmail("a@b.com");
        user.setPassword("Abcd9876");
        userRepository.save(user);
        Sector sector= new Sector(SectorName.CONFERIMENTO);
        UserSectorPermission userSectorPermission= new UserSectorPermission(1L,user,sector,true,true,true,true);
        HashMap<SectorName, UserSectorPermission> permission =new HashMap<SectorName,UserSectorPermission>();
        permission.put(SectorName.CONFERIMENTO,userSectorPermission);
        updatePermissionsRequest = new UpdatePermissionsRequest("a@b.com",permission);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(userRepository);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/user")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePermissionsRequest)))
                .andExpect(status().isOk());
    }




}
