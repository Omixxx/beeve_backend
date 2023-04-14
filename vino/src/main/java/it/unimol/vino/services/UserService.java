package it.unimol.vino.services;


import it.unimol.vino.exceptions.PermissionAlreadyAssigned;
import it.unimol.vino.exceptions.PermissionNotFound;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Permission;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public UserService(UserRepository userRepository, PermissionService permissionService) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    public Optional<User> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void assignPermission(String userEmail, String permissionSector) {
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UserNotFoundException("User with email " + userEmail + " not found"));
        Permission permission = this.permissionService.getPermissionBySector(permissionSector).orElseThrow(
                () -> new PermissionNotFound("Permission " + permissionSector + " not found"));

        if (user.getPermissions().contains(permission))
            throw new PermissionAlreadyAssigned("User already has " + permission.getSector() + "permission");

        user.getPermissions().add(permission);
        this.userRepository.save(user);
    }

}
