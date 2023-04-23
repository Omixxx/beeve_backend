
package it.unimol.vino.services;

import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public boolean isCategoryPresent(String categoryName) {
        return this.categoryRepository.findByName(categoryName).isPresent();
    }
    public List<Category> getAllCategory(){
     return this.categoryRepository.findAll();}

    public Category postCategory(Category category) throws CategoryExistingException {
        if(this.categoryRepository.findByName(category.getName()).isPresent())
            throw new CategoryExistingException("categoria già esistente");
        var cate = Category.builder()
                .name(category.getName())
                .build();

         return this.categoryRepository.save(cate);

    }

        public void deleteCategory(String categoryName)throws CategoryNotFoundException {

            if(this.categoryRepository.findByName(categoryName).isEmpty()){
                throw new CategoryNotFoundException("categoria non trovata");
            }
               this.categoryRepository.deleteByName(categoryName);
    }

}
