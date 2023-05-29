package it.unimol.vino.repository;

import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRoleNot(Role role);
}
