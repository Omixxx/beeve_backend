package it.unimol.vino.repository;

import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserProgressesProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProgressProcessRepository extends JpaRepository<UserProgressesProcess, Long> {

}
