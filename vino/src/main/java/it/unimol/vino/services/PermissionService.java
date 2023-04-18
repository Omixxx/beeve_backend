package it.unimol.vino.services;

import it.unimol.vino.exceptions.PermissionNotFound;
import it.unimol.vino.models.entity.Permission;
import it.unimol.vino.models.enums.Sector;
import it.unimol.vino.repository.PermissionRepository;
import it.unimol.vino.utils.Logger;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void addPermission(Permission permission) {
        if (this.permissionRepository.findPermissionBySector(permission.getSector()).isPresent()) {
            Logger.getLogger().warn("Permission " + permission.getSector() + " already exist");
            return;
        }
        this.permissionRepository.save(permission);
    }

    public Permission getPermission(Sector sector) {
        return this.permissionRepository.findPermissionBySector(sector).orElseThrow(
                () -> new PermissionNotFound("Permission " + sector + " not found"));
    }

}


