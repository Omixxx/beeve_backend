package it.unimol.vino.entity;

import it.unimol.vino.models.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryEntityTest {

    @Test
    void getName_withCategory_shouldReturnCategoryName() {
        String categoryName = "categoryName";
        Category category = Category.builder()
                .name(categoryName)
                .build();

        Assertions.assertEquals(category.getName(), categoryName);
    }
}