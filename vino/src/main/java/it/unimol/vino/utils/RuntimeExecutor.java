package it.unimol.vino.utils;

import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class RuntimeExecutor implements CommandLineRunner {
    private final SectorRepository sectorRepository;
    private final StateRepository stateRepository;

    @Override
    public void run(String... args) {
        Stream.of(SectorName.values()).forEach(sectorName -> {
            Sector permission = new Sector(sectorName);
            if (this.sectorRepository.findSectorBySectorName(permission.getSectorName()).isEmpty())
                this.sectorRepository.save(permission);
        });

        if (this.stateRepository.findByName("Completato").isEmpty()) {
            this.stateRepository.save(State.builder().name("Completato")
                    .doesProduceWaste(false)
                    .build()
            );
        }
    }
}
