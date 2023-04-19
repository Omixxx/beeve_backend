package it.unimol.vino.controllers;

import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;
import it.unimol.vino.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/update_permissions")
    public ResponseEntity<UpdatePermissionResponse> updatePermissions(@RequestBody @Valid UpdatePermissionsRequest updatePermissionsRequest) {
        return ResponseEntity.ok(this.userService.updatePermissions(updatePermissionsRequest));
    }
}
