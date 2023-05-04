package it.unimol.vino.controllers;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryAlreadyExistingException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.request.CategoryRequest;
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
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok(this.service.getAllCategory());

    }
    @GetMapping("/{categoryName}")
    public ResponseEntity<Boolean>isCategoryPresent(@Valid @RequestBody   String categoryName)  {
        return ResponseEntity.ok( this.service.isCategoryPresent(categoryName));
    }
    @DeleteMapping("/{categoryName}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryName) throws CategoryNotFoundException {
        this.service.deleteCategory(categoryName);
        return ResponseEntity.ok("success");
    }

    @PostMapping
    public ResponseEntity<String> postCategory(@Valid @RequestBody CategoryRequest request)throws CategoryAlreadyExistingException {
        return ResponseEntity.ok(this.service.postCategory(request));

    }

    }

