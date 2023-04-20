
package it.unimol.vino.repository;
import it.unimol.vino.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   List<Category> findByCategory(String category);

    void deleteCategory(String upperCase);
}
