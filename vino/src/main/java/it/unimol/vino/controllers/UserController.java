package it.unimol.vino.controllers;

import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;
import it.unimol.vino.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/update_permissions")
    public ResponseEntity<UpdatePermissionResponse> updatePermissions(
            @Valid @RequestBody
            UpdatePermissionsRequest updatePermissionsRequest
    ) {
        return ResponseEntity.ok(this.userService.updatePermissions(updatePermissionsRequest));
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<UserSectorPermission>> getPermissions() {
        return ResponseEntity.ok(this.userService.getPermissions());
    }
}
