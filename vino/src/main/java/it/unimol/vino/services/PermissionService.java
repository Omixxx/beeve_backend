package it.unimol.vino.services;

import it.unimol.vino.models.entity.Permission;
import it.unimol.vino.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void addPermission(Permission permission) {
        this.permissionRepository.save(permission);
    }

    public Optional<Permission> getPermissionBySector(String sector) {
        return this.permissionRepository.findPermissionBySector(sector);
    }
}
