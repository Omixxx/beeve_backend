package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Contribution;
import it.unimol.vino.models.entity.GrapeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution,Long> {

    List<Contribution> findByOrigin(String origin);

    List<Contribution> findByCountry(String country);

    List<Contribution> findBySugarDegree(double sugarDegree);

    List<Contribution> findBySugarDegreeGreaterThanEqual(double sugarDegree);

    List<Contribution> findBySugarDegreeLessThanEqual(double sugarDegree);

    List<Contribution> findByQuantity(double quantity);

    List<Contribution> findByQuantityGreaterThanEqual(double quantity);

    List<Contribution> findByQuantityLessThanEqual(double quantity);

    List<Contribution> findByAssociatedGrapeType(GrapeType grapeType);

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
