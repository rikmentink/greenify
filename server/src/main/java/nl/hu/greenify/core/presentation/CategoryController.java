package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.CategoryService;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.factor.Factor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends Controller {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@RequestParam Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category.getName(), category.getColor(), category.getDescription());
        return ResponseEntity.ok(createdCategory);
    }
    @PostMapping("/{id}/factors")
    public ResponseEntity<?> addFactorToCategory(@PathVariable Long id, @RequestBody Factor factor) {
        Category updatedCategory = categoryService.addFactorToCategory(id, factor);
        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCategory);
    }
}

