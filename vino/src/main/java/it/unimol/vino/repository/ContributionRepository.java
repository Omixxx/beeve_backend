package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContributionRepository extends JpaRepository<Contribution,Long> {

    Optional<List<Contribution>> findByOrigin(String origin);

    Optional<List<Contribution>> findByCountry(String country);

    Optional<List<Contribution>> findBySugarDegree(double sugarDegree);

    Optional<List<Contribution>> findBySugarDegreeGreaterThanEqual(double sugarDegree);

    Optional<List<Contribution>> findBySugarDegreeLessThanEqual(double sugarDegree);

    Optional<List<Contribution>> findByQuantity(double quantity);

    Optional<List<Contribution>> findByQuantityGreaterThanEqual(double quantity);

    Optional<List<Contribution>> findByQuantityLessThanEqual(double quantity);

    Optional<List<Contribution>> findByAssociatedGrapeType(GrapeType grapeType);

    void deleteAllByOrigin(String origin);

    void deleteAllByCountry(String country);

    void deleteAllBySugarDegree(double sugarDegree);

    void deleteAllBySugarDegreeGreaterThanEqual(double sugarDegree);

    void deleteAllBySugarDegreeLessThanEqual(double sugarDegree);

    void deleteAllByQuantity(double quantity);

    void deleteAllByQuantityGreaterThanEqual(double quantity);

    void deleteAllByQuantityLessThanEqual(double quantity);

    void deleteAllByAssociatedGrapeType(GrapeType grapeType);

    List<Contribution> findBySugarDegreeBetween(double sugarDegreeMin, double sugarDegreeMax);

    List<Contribution> findByQuantityBetween(double quantityMin, double quantityMax);
}
