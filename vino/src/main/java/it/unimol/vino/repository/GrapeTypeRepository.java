package it.unimol.vino.repository;

import it.unimol.vino.models.entity.GrapeType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GrapeTypeRepository extends JpaRepository<GrapeType,Long>{
    
    void delete(String name);

    boolean existsById(String name);

    Optional<GrapeType> findById(String id);



    Optional<List<GrapeType>> findByColor(String color);

    Optional<List<GrapeType>> findBySpecies(String species);

    void deleteAllByColor(String color);

    void deleteAllBySpecies(String species);
}
