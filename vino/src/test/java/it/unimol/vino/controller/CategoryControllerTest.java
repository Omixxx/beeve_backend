/*package it.unimol.vino.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import it.unimol.vino.controllers.CategoryController;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryAlreadyExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.services.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.unimol.vino.exceptions.CategoryExistingException;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;

@SpringBootTest
public class CategoryControllerTest {

    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = org.mockito.Mockito.mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    public void testGetAllCategory() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Food"));
        categories.add(new Category("Drink"));

        when(categoryService.getAllCategory()).thenReturn(categories);

        ResponseEntity<List<Category>> responseEntity = categoryController.getAllCategory();

        assertEquals(2, responseEntity.getBody().size());
        verify(categoryService, times(1)).getAllCategory();
    }

    @Test
    public void testIsCategoryPresent() {
        String categoryName = "Food";

        when(categoryService.isCategoryPresent(categoryName)).thenReturn(true);

        ResponseEntity<Boolean> responseEntity = categoryController.isCategoryPresent(categoryName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody());
        verify(categoryService, times(1)).isCategoryPresent(categoryName);
    }

    @Test
    public void testDeleteCategory() throws CategoryNotFoundException {
        String categoryName = "Food";

        ResponseEntity<String> responseEntity = categoryController.deleteCategory(categoryName);

        ResponseEntity<?> responseEntity = categoryController.deleteCategory(categoryName);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody());

        // verify that the service method was called once with the expected argument
        verify(categoryService, times(1)).deleteCategory(categoryName);
    }

    @Test
    public void testPostCategory() throws CategoryExistingException {
        Category category = new Category("Food");

        when(categoryService.postCategory(category)).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryController.postCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Food", responseEntity.getBody().getName());
        verify(categoryService, times(1)).postCategory(category);
    }

    /*
    @Test
    void postCategory_withValidCategory_shouldReturnSavedCategory() throws CategoryAlreadyExistingException {
        CategoryRequest newCategory = new CategoryRequest("newCategory");

        when(categoryService.postCategory(any(CategoryRequest.class))).thenReturn("Registrato");

        ResponseEntity<String> result = categoryController.postCategory(newCategory);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody().equals("Registrato"), newCategory.getName());
    }

    @Test
    void postCategory_withExistingCategory_shouldThrowException() throws CategoryAlreadyExistingException {
        Category existingCategory = new Category("existingCategory");

        when(categoryService.postCategory(any(Category.class))).thenThrow(CategoryAlreadyExistingException.class);

        assertThrows(CategoryAlreadyExistingException.class, () -> categoryController.postCategory(existingCategory));

        verify(categoryService, times(1)).postCategory(any(Category.class));
    }


}*/
