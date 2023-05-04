
package it.unimol.vino.services;

import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryAlreadyExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public String postCategory(CategoryRequest request) throws CategoryAlreadyExistingException {
        if(this.categoryRepository.findByName(request.getName()).isPresent())
            throw new CategoryAlreadyExistingException("categoria già esistente");

        var category = Category.builder()
                .name(request.getName())
                .itemList(new ArrayList<>())
                .build();

        this.categoryRepository.save(category);
         return "Registrato";

    }

        public void deleteCategory(String categoryName)throws CategoryNotFoundException {

            if(this.categoryRepository.findByName(categoryName).isEmpty()){
                throw new CategoryNotFoundException("categoria non trovata");
            }
               this.categoryRepository.deleteByName(categoryName);
    }

}
