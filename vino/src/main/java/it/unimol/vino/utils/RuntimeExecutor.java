package it.unimol.vino.utils;

import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class RuntimeExecutor implements CommandLineRunner {
    private final SectorRepository sectorRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

        if (this.userRepository.findByEmail("dimenna@unimol.it").isEmpty()) {
            User user = User.builder()
                    .firstName("Lorenzo")
                    .lastName("Di Menna")
                    .email("dimenna@unimol.it")
                    .age(22)
                    .password(passwordEncoder.encode("Admin123"))
                    .permissions(new ArrayList<>())
                    .role(Role.ADMIN)
                    .build();

            List<Sector> sectors = this.sectorRepository.findAll();
            sectors.forEach(user::addPermission);
            this.userRepository.save(user);
        }
    }
}
