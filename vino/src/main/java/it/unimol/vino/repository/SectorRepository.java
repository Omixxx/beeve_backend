package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.enums.SectorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findSectorBySectorName(SectorName sectorName);

    Optional<List<Sector>> findSectorsBySectorName(SectorName sectorName);
}
