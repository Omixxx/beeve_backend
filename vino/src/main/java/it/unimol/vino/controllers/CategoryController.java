package it.unimol.vino.controllers;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService service;


    public CategoryController(CategoryService service) {
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok(this.service.getAllCategory());

    }
    @GetMapping("/is_present")
    public ResponseEntity<Boolean>isCategoryPresent(@Valid @RequestParam String categoryName)  {
        return ResponseEntity.ok( this.service.isCategoryPresent(categoryName));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@RequestParam String categoryName) throws CategoryNotFoundException {
        this.service.deleteCategory(categoryName);
        return ResponseEntity.ok("success");
    }

    @PostMapping
    public ResponseEntity<Category> postCategory(@Valid @RequestBody Category category)throws CategoryExistingException {
        return ResponseEntity.ok(this.service.postCategory(category));

    }
}

