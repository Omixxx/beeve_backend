package it.unimol.vino.controller;

import it.unimol.vino.controllers.CategoryController;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testGetAllCategory() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Category 1").build());
        categories.add(Category.builder().name("Category 2").build());

        when(categoryService.getAllCategory()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Category 2"))
                .andDo(print());
    }

    @Test
    void testIsCategoryPresent() throws Exception {
        when(categoryService.isCategoryPresent("Category 1")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/is_present")
                        .param("categoryName", "Category 1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(print());
    }

    @Test
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory("Category 1");

        mockMvc.perform(delete("/api/v1/category")
                        .param("categoryName", "Category 1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }

}