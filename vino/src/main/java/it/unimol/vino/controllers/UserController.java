package it.unimol.vino.controllers;

import it.unimol.vino.models.request.UpdatePermissionsRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @PutMapping("/update_permissions")
    public void updatePermissions(@Valid UpdatePermissionsRequest updatePermissionsRequest){

    }


}
