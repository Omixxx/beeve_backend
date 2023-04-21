
package it.unimol.vino.services;

import it.unimol.vino.exceptions.DeleteCategoryException;
import it.unimol.vino.exceptions.PutCategoryException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public boolean isCategoryPresent(String categoryName) {
        Category category=this.categoryRepository.findByName(categoryName.toUpperCase());
        if(category!= null)
            return true;
        return false;
    }
    public List<Category> getAllCategory(){
     return this.categoryRepository.findAll();}

    public Category putCategory(Category category) throws PutCategoryException {
        Category control=this.categoryRepository.findByName(category.getName());
        if(control!=null)
            throw new PutCategoryException("category already exists");
        return this.categoryRepository.save(category);
    }

        public void deleteCategory(String categoryname)throws DeleteCategoryException {
           Category categoryFound= categoryRepository.findByName(categoryname.toUpperCase());
            if(categoryFound.getName().isEmpty()){
                throw new DeleteCategoryException("category not found");
            }
               this.categoryRepository.delete(categoryFound);
    }

}
