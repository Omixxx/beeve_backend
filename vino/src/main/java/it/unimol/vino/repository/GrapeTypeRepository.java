package it.unimol.vino.repository;

import it.unimol.vino.models.entity.GrapeType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GrapeTypeRepository extends JpaRepository<GrapeType, String> {

}
