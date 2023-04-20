package it.unimol.vino.controllers;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.unimol.vino.models.entity.Category.*;

@RestController
@RequestMapping("/Category")
public class CategoryController {
    private final CategoryService service;


    public CategoryController(CategoryService service) {
        this.service = service;
    }
    @GetMapping
    public List<Category> getAllCategory(){
        return this.service.getAllCategory();
    }
    @GetMapping("/{category}")
    public boolean getByCategory(@PathVariable String category){
        boolean control=this.service.getByCategory(category);
        return control;

    }
    @DeleteMapping("/{category}")
    public void deleteCategory(@PathVariable String category){
        this.service.deleteCategory(category);
    }
    @PostMapping
    public Category putCategory(@Valid @RequestBody Category category){
        return this.service.putCategory(category);
    }

    }

