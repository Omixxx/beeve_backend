package it.unimol.vino.service;

import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void isCategoryPresent_withCategoryPresent_shouldReturnTrue() {
        Category category = new Category("categoryName");
        when(categoryRepository.findByName("categoryName")).thenReturn(Optional.of(category));

        boolean result = categoryService.isCategoryPresent("categoryName");

        assertTrue(result);
    }

    @Test
    void isCategoryPresent_withCategoryNotPresent_shouldReturnFalse() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        boolean result = categoryService.isCategoryPresent("missingCategoryName");

        assertFalse(result);
    }

    @Test
    void getAllCategory_shouldReturnAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("category1"));
        categoryList.add(new Category("category2"));

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getAllCategory();

        assertEquals(result.size(), 2);
    }

    @Test
    void postCategory_withMissingCategory_shouldReturnSavedCategory() throws CategoryExistingException {
        Category newCategory = new Category("newCategory");
        Category existingCategory = new Category("existingCategory");

        when(categoryRepository.findByName("newCategory")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category result = categoryService.postCategory(newCategory);

        assertNotNull(result);
        assertEquals(result.getName(), newCategory.getName());

        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, never()).findByName(existingCategory.getName());
    }

    @Test
    void postCategory_withExistingCategory_shouldThrowException() {
        Category category = new Category("existingCategory");

        when(categoryRepository.findByName("existingCategory")).thenReturn(Optional.of(category));

        assertThrows(CategoryExistingException.class, () -> {
            categoryService.postCategory(category);
        });

        verify(categoryRepository, never()).save(any(Category.class));
        verify(categoryRepository, times(1)).findByName(category.getName());
    }

    @Test
    void deleteCategory_withExistingCategory_shouldDeleteCategory() throws CategoryNotFoundException {
        Category category = new Category("categoryToDelete");

        when(categoryRepository.findByName("categoryToDelete")).thenReturn(Optional.of(category));

        categoryService.deleteCategory("categoryToDelete");

        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, times(1)).deleteByName(category.getName());
    }

    @Test
    void deleteCategory_withMissingCategory_shouldThrowException() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteCategory("missingCategory");
        });

        verify(categoryRepository, never()).deleteByName(anyString());
    }
}
