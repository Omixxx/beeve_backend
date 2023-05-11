package it.unimol.vino.controllers;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.CategoryExistingException;
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

    @PostMapping
    public ResponseEntity<Category> postCategory(@Valid @RequestBody CategoryRequest category)throws CategoryExistingException {
        return ResponseEntity.ok(this.service.postCategory(category));

    }

    }

