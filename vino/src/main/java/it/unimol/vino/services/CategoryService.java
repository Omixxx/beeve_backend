
package it.unimol.vino.services;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.models.entity.Category;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean getByCategory(String category) {
        return this.categoryRepository.findByCategory(category.toUpperCase()).isEmpty();
    }
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();}
    public Category putCategory(@Valid Category category) {
        return this.categoryRepository.save(category);}
    public void deleteCategory(String category) {
        this.categoryRepository.deleteCategory(category.toUpperCase());
    }
}
