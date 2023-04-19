package it.unimol.vino.services;

import it.unimol.vino.exceptions.PermissionNotFound;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.repository.PermissionRepository;
import it.unimol.vino.utils.Logger;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void addPermission(Sector sector) {
        if (this.permissionRepository.findPermissionBySectorName(sector.getSectorName()).isPresent()) {
            Logger.getLogger().warn("Permission " + sector.getSectorName() + " already exist");
            return;
        }
        this.permissionRepository.save(sector);
    }

    public Sector getPermission(SectorName sectorName) {
        return this.permissionRepository.findPermissionBySectorName(sectorName).orElseThrow(
                () -> new PermissionNotFound("Permission " + sectorName + " not found"));
    }

}


