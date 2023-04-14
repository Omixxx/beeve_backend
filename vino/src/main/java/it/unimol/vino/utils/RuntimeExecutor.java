package it.unimol.vino.utils;

import it.unimol.vino.models.entity.Permission;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.repository.UserRepository;
import it.unimol.vino.services.PermissionService;
import it.unimol.vino.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RuntimeExecutor implements CommandLineRunner {
    private final PermissionService permissionService;
    private final UserService userService;
    private final UserRepository userRepository;

    public RuntimeExecutor(PermissionService permissionService, UserService userService, UserRepository userRepository) {
        this.permissionService = permissionService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.permissionService.addPermission(new Permission("Pigiatura"));
        User user = User.builder()
                .firstName("a")
                .lastName("b")
                .email("a@b")
                .password("f")
                .role(Role.USER)
                .build();
        this.userRepository.save(user);
        this.userService.assignPermission("a@b", "Pigiatura");
    }
}
