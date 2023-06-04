package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contribution,Long> {


}
