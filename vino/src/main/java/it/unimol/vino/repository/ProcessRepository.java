package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    List<Process> findByCurrentStateNotNull();
}
