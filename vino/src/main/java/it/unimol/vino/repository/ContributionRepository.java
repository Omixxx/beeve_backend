package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContributionRepository extends JpaRepository<Contribution,Long> {


}
