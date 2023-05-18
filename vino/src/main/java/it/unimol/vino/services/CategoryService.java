
package it.unimol.vino.services;

import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryAlreadyExistingException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public boolean isCategoryPresent(String categoryName) {
        return this.categoryRepository.findByName(categoryName.toUpperCase()).isPresent();
    }
    public List<Category> getAllCategory(){
     return this.categoryRepository.findAll();}

    public Category postCategory(CategoryRequest request) {
        if(this.isCategoryPresent(request.getName()))
            throw new CategoryAlreadyExistingException("categoria già esistente");


        var category = Category.builder()
                .name(request.getName().toUpperCase())
                .itemList(new ArrayList<>())
                .build();

        this.categoryRepository.save(category);
        return category;

    }

    public void deleteCategory(String categoryName)throws CategoryNotFoundException {

        Category category = this.categoryRepository.findByName(categoryName).orElseThrow(
                () -> new CategoryNotFoundException("La categoria con nome: " + categoryName + " non è stata trovata")
        );

        this.categoryRepository.delete(category);
    }

}
