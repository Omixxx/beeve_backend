/*package it.unimol.vino.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import it.unimol.vino.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import it.unimol.vino.exceptions.CategoryExistingException;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryRepository = org.mockito.Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void testGetAllCategory() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Food"));
        categories.add(new Category("Drink"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategory();

        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
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
    public void testIsCategoryPresentWhenNotExists() {
        assertFalse(categoryService.isCategoryPresent("Non existing category"));
    }

    @Test
    public void testPostCategoryWithExistingCategory() {
        Category existingCategory = new Category("Food");

        when(categoryRepository.findByName(existingCategory.getName())).thenReturn(Optional.of(existingCategory));

        assertThrows(CategoryExistingException.class, () -> categoryService.postCategory(existingCategory));
        verify(categoryRepository, times(1)).findByName(existingCategory.getName());
    }

    @Test
    void deleteCategory_withExistingCategory_shouldDeleteCategory() throws CategoryNotFoundException {
        Category category = new Category("categoryToDelete");

        @Test
        public void testDeleteNonExistingCategory() {
            assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory("Non existing category"));
        }

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


}*/
