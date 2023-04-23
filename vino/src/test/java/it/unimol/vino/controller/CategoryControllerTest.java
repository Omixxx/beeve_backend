package it.unimol.vino.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import it.unimol.vino.controllers.CategoryController;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryControllerTest {

    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    void getAllCategory_shouldReturnAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("category1"));
        categoryList.add(new Category("category2"));

        when(categoryService.getAllCategory()).thenReturn(categoryList);

        ResponseEntity<List<Category>> result = categoryController.getAllCategory();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody().size(), 2);
    }

    @Test
    void isCategoryPresent_shouldReturnTrue() {
        when(categoryService.isCategoryPresent(anyString())).thenReturn(true);

        ResponseEntity<Boolean> result = categoryController.isCategoryPresent("categoryName");

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody());
    }

    @Test
    void deleteCategory_withValidCategory_shouldReturn200OK() throws CategoryNotFoundException {
        String categoryName = "existingCategoryName";

        doNothing().when(categoryService).deleteCategory(categoryName);

        ResponseEntity<?> responseEntity = categoryController.deleteCategory(categoryName);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody());

        // verify that the service method was called once with the expected argument
        verify(categoryService, times(1)).deleteCategory(categoryName);
    }

    @Test
    void deleteCategory_withInvalidCategory_shouldThrowException() throws CategoryNotFoundException {
        String categoryName = "missingCategoryName";

        doThrow(CategoryNotFoundException.class).when(categoryService).deleteCategory(categoryName);

        assertThrows(CategoryNotFoundException.class, () -> categoryController.deleteCategory(categoryName));

        // verify that the service method was called once with the expected argument
        verify(categoryService, times(1)).deleteCategory(categoryName);
    }

    @Test
    void postCategory_withValidCategory_shouldReturnSavedCategory() throws CategoryExistingException {
        Category newCategory = new Category("newCategory");

        when(categoryService.postCategory(any(Category.class))).thenReturn(newCategory);

        ResponseEntity<Category> result = categoryController.postCategory(newCategory);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody().getName(), newCategory.getName());
    }

    @Test
    void postCategory_withExistingCategory_shouldThrowException() throws CategoryExistingException {
        Category existingCategory = new Category("existingCategory");

        when(categoryService.postCategory(any(Category.class))).thenThrow(CategoryExistingException.class);

        assertThrows(CategoryExistingException.class, () -> categoryController.postCategory(existingCategory));

        verify(categoryService, times(1)).postCategory(any(Category.class));
    }
}