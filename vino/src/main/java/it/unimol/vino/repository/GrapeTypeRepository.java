package it.unimol.vino.repository;

import it.unimol.vino.models.entity.GrapeType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GrapeTypeRepository extends JpaRepository<GrapeType,Long>{
    
    void deleteById(String name);

    boolean existsById(String name);

    GrapeType findById(String name);

    List<GrapeType> findByColor(String color);

    List<GrapeType> findBySpecies(String species);

}
