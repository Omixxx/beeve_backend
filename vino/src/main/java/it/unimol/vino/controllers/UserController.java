package it.unimol.vino.controllers;

import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.dto.UserPermissionDTO;
import it.unimol.vino.models.request.ChangePasswordRequest;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;
import it.unimol.vino.services.UserService;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/update_permissions")
    public ResponseEntity<UpdatePermissionResponse> updatePermissions(
            @Valid @RequestBody UpdatePermissionsRequest updatePermissionsRequest, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is updating permissions");
        return ResponseEntity.ok(this.userService.updatePermissions(updatePermissionsRequest));
    }

    @GetMapping("/user_permissions")
    public ResponseEntity<List<UserPermissionDTO>> getPermissions(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting a user permissions list");
        return ResponseEntity.ok(this.userService.getPermissions());
    }

    @GetMapping("/users_permissions")
    public ResponseEntity<List<UserPermissionDTO>> getUsersPermissions(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting the permissions list of all users");
        return ResponseEntity.ok(this.userService.getAllPermissions());
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting his info");
        return ResponseEntity.ok(this.userService.getUserInfo());
    }

    @PostMapping("/change/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is changing password");
        this.userService.changePassword(changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());
        return ResponseEntity.ok("Password aggiornata con successo");
    }
}
