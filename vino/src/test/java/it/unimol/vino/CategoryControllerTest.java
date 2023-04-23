package it.unimol.vino.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import it.unimol.vino.controllers.CategoryController;
import it.unimol.vino.exceptions.DeleteCategoryException;
import it.unimol.vino.exceptions.PutCategoryException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;

@SpringBootTest
public class CategoryControllerTest {

    @Mock
    CategoryService service;

    @InjectMocks
    CategoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategory() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category());
        categories.add(new Category());
        categories.add(new Category());

        Mockito.when(service.getAllCategory()).thenReturn(categories);

        ResponseEntity<List<Category>> response = controller.getAllCategory();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(categories.size());
    }

    @Test
    void testIsCategoryPresent() {
        String categoryName = "categoria1";

        Mockito.when(service.isCategoryPresent(categoryName)).thenReturn(true);

        ResponseEntity<Boolean> response = controller.isCategoryPresent(categoryName);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(true);
    }

    @Test
    void testDeleteCategory() throws DeleteCategoryException {
        String categoryName = "categoria_da_eliminare";
        Category category = new Category();
        category.setName(categoryName);

        Mockito.doNothing().when(service).deleteCategory(categoryName);
       // assertThat(controller.deleteCategory(categoryName)).isEqualTo("success");
    }

    @Test
    void testPutCategory() throws PutCategoryException {
        String categoryName = "categoria_da_inserire";
        Category category = new Category();
        category.setName(categoryName);

        Mockito.when(service.putCategory(category)).thenReturn(category);

        ResponseEntity<Category> response = controller.putCategory(category);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(categoryName);
    }

    @Test
    void testPutCategoryWithNonExistingCategoryName() throws PutCategoryException {
        String categoryName = "categoria_non_esistente";
        Category category = new Category();
        category.setName(categoryName);

        Mockito.when(service.putCategory(category)).thenThrow(new PutCategoryException("Categoria non presente"));
        ResponseEntity<Category> response = controller.putCategory(category);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getName()).isNull();
        //assertThat(response.getBody().getErrorMessage()).isEqualTo("Categoria non presente");
    }
}