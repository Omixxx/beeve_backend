package it.unimol.vino.controllers;
import it.unimol.vino.exceptions.DeleteCategoryException;
import it.unimol.vino.exceptions.PutCategoryException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi/category")
public class CategoryController {
    private final CategoryService service;


    public CategoryController(CategoryService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok(this.service.getAllCategory());

    }
    @GetMapping("/{category}")
    public ResponseEntity<Boolean>isCategoryPresent(@Valid @RequestBody   String categoryname)  {
        return ResponseEntity.ok( this.service.isCategoryPresent(categoryname));
    }
    @DeleteMapping("/{category}")
    public void deleteCategory(@PathVariable String categoryname) throws DeleteCategoryException {
        this.service.deleteCategory(categoryname);
        ResponseEntity.ok("succes");
    }

    @PutMapping
    public ResponseEntity<Category> putCategory(@Valid @RequestBody Category category)throws PutCategoryException {
        return ResponseEntity.ok( this.service.putCategory(category));

    }

    }

