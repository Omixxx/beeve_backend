package it.unimol.vino.repository;

import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {

}
