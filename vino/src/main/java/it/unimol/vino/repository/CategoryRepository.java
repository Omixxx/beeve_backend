package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
    Category findByName(String categoryName);

}