package it.unimol.vino.service;

import it.unimol.vino.exceptions.CategoryAlreadyExistingException;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsCategoryPresent() {
        String categoryName = "Red Wines";
        when(categoryRepository.findByName(categoryName.toUpperCase())).thenReturn(Optional.of(new Category()));
        boolean isPresent = categoryService.isCategoryPresent(categoryName);
        Assertions.assertTrue(isPresent);

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        isPresent = categoryService.isCategoryPresent("Non Existing Category");
        Assertions.assertFalse(isPresent);
    }

    @Test
    void testGetAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder().name("Red Wines").build());
        categoryList.add(Category.builder().name("White Wines").build());
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> allCategories = categoryService.getAllCategory();
        Assertions.assertEquals(2, allCategories.size());
        Assertions.assertEquals(categoryList, allCategories);
    }

    @Test
    void testPostCategory() throws CategoryAlreadyExistingException {
        String categoryName = "Red Wines";
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(categoryName);

        when(categoryRepository.findByName(categoryName.toUpperCase())).thenReturn(Optional.empty());
        when(categoryRepository.save(Category.builder().name(categoryName.toUpperCase()).itemList(new ArrayList<>()).build()))
                .thenReturn(Category.builder().name(categoryName.toUpperCase()).itemList(new ArrayList<>()).build());
        Category category = categoryService.postCategory(categoryRequest);
        Assertions.assertEquals(categoryName.toUpperCase(), category.getName());
    }

    @Test
    void testDeleteCategory() throws CategoryNotFoundException {
        String categoryName = "RED WINES";
        Category category = Category.builder().name(categoryName.toUpperCase()).itemList(new ArrayList<>()).build();
        when(categoryRepository.findByName(categoryName.toUpperCase())).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryName);
    }

    @Test
    void testDeleteNonExistingCategory() {
        String categoryName = "Non Existing Category";
        when(categoryRepository.findByName(categoryName.toUpperCase())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(categoryName));
    }

    @Test
    void testPostExistingCategory() {
        String categoryName = "Red Wines";
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(categoryName);

        when(categoryRepository.findByName(categoryName.toUpperCase())).thenReturn(Optional.of(Category.builder().build()));
        Assertions.assertThrows(CategoryAlreadyExistingException.class, () -> categoryService.postCategory(categoryRequest));
    }
}

