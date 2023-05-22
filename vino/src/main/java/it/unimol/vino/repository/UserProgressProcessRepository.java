package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.entity.UserProgressesProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProgressProcessRepository extends JpaRepository<UserProgressesProcess, Long> {

    List<UserProgressesProcess> findByProcessAndCompletedState(Process process, State completedState);
}
