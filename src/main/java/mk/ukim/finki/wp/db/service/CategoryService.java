package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.Category;
import mk.ukim.finki.wp.db.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public void addCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
    }

    public void editCategory(Integer id, String name, String description) {
        Category category = categoryRepository.findById(id).get();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
