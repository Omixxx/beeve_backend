/*package it.unimol.vino.controller;

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
import it.unimol.vino.models.request.PermissionsRequest;
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
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

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
    public void postUpdatePermissionTestOk() throws Exception {
        User user;
        if(!sectorRepository.findSectorBySectorName(SectorName.PROCESSO).isPresent()){
            System.err.println("Sector non presente");
        Sector sector= new Sector(SectorName.PROCESSO);
        sectorRepository.save(sector);}
        List<UserSectorPermission> list = new ArrayList<>();
        UserSectorPermission userSectorPermission = new UserSectorPermission();
        user = new User();
        user.setRole(Role.USER);
        user.setFirstName("C");
        user.setLastName("H");
        user.setEmail("a@n.com");
        user.setPassword("Abcd9876");
        userSectorPermission.setUser(user);
        list.add(userSectorPermission);
        user.setPermissions(list);
        List<Sector> sectors = this.sectorRepository.findAll();
        sectors.forEach(user::addPermission);
        userRepository.save(user);
        objectMapper = new ObjectMapper();
        tokenClass = new AuthToken(sectorRepository, userRepository);
        PermissionsRequest permissionsRequest =new PermissionsRequest(true,true,true,true);
        HashMap<SectorName,PermissionsRequest> permissionsRequestHashMap= new HashMap<>();
        permissionsRequestHashMap.put(SectorName.PROCESSO,permissionsRequest);
        UpdatePermissionsRequest updatePermissionsRequest  =new UpdatePermissionsRequest("a@n.com",permissionsRequestHashMap);




        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/user/update_permissions")
                        .header("Authorization", "Bearer " + tokenClass.generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePermissionsRequest)))
                .andExpect(status().isOk());
    }

}*/

