
package it.unimol.vino.services;

import it.unimol.vino.exceptions.DeleteCategoryException;
import it.unimol.vino.exceptions.PutCategoryException;
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

    public Category putCategory(Category category) throws PutCategoryException {
        if(this.categoryRepository.findByName(category.getName()).isPresent())
            throw new PutCategoryException("categoria già esistente");
        return this.categoryRepository.save(category);
    }

        public void deleteCategory(String categoryname)throws DeleteCategoryException {

            if(this.categoryRepository.findByName(categoryname).isEmpty()){
                throw new DeleteCategoryException("categoria non trovata");
            }
               this.categoryRepository.deleteByName(categoryname);
    }

}
