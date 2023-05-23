package it.unimol.vino.repository;

import it.unimol.vino.models.entity.GrapeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GrapeTypeRepository extends JpaRepository<GrapeType, Long> {
    List<GrapeType> findBySpecies(String name);
    Optional<GrapeType> findBySpeciesAndColor(String species, String color);

}
