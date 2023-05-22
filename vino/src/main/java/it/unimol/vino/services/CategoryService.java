
package it.unimol.vino.services;

import it.unimol.vino.dto.CategoryDTO;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryAlreadyExistingException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<CategoryDTO> getAllCategory() {
        return this.categoryRepository.findAll().stream().map(category ->
                CategoryDTO.builder()
                        .name(category.getName())
                        .isPrimary(category.getIsPrimary())
                        .build()
        ).toList();
    }

    public Category postCategory(@NotNull @Valid CategoryRequest request) {
        if (this.isCategoryPresent(request.getName()))
            throw new CategoryAlreadyExistingException("categoria già esistente");

        if (Objects.isNull(request.getIsPrimary()))
            throw new CategoryAlreadyExistingException("è necessario specificare se la categoria è primaria o meno");

        var category = Category.builder()
                .name(request.getName().toUpperCase())
                .isPrimary(request.getIsPrimary())
                .itemList(new ArrayList<>())
                .build();

        return this.categoryRepository.save(category);
    }

    public void deleteCategory(String categoryName) throws CategoryNotFoundException {

        Category category = this.categoryRepository.findByName(categoryName).orElseThrow(
                () -> new CategoryNotFoundException("La categoria con nome: " + categoryName + " non è stata trovata")
        );

        this.categoryRepository.delete(category);
    }

}
