package it.unimol.vino.utils;

import it.unimol.vino.models.entity.Permission;
import it.unimol.vino.models.enums.Sector;
import it.unimol.vino.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class RuntimeExecutor implements CommandLineRunner {
    PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        Stream.of(Sector.values()).forEach(sector -> {
            Permission permission = new Permission(sector);
            if (this.permissionRepository.findPermissionBySector(permission.getSector()).isEmpty())
                this.permissionRepository.save(permission);
        });
    }
}
