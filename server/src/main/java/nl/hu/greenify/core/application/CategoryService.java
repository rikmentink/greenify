package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.CategoryRepository;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.factor.Factor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final FactorService factorService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, FactorService factorService) {
        this.categoryRepository = categoryRepository;
        this.factorService = factorService;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(String name, String color, String description) {
        Category newCategory = Category.createCategory(name, color, description);
        return categoryRepository.save(newCategory);
    }

    public Category addFactorToCategory(Long categoryId, Factor factor) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.addFactor(factor);
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }
}

