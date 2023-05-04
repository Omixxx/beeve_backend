package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,String> {

   Optional<Category> findByName(String categoryName);

}