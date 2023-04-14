package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    public Optional<Permission> findPermissionBySector(String sector);
}
