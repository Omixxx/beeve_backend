package it.unimol.vino.utils;

import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.repository.SectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class RuntimeExecutor implements CommandLineRunner {
    private final SectorRepository sectorRepository;

    @Override
    public void run(String... args) {
        Stream.of(SectorName.values()).forEach(sectorName -> {
            Sector permission = new Sector(sectorName);
            if (this.sectorRepository.findSectorBySectorName(permission.getSectorName()).isEmpty())
                this.sectorRepository.save(permission);
        });
    }
}
