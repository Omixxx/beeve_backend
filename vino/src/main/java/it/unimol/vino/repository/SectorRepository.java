package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.enums.SectorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findPermissionBySectorName(SectorName sectorName);
}
