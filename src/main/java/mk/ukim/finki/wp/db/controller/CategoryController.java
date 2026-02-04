package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getCategoryPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/category";
    }

    @GetMapping("/add/category")
    public String getAddCategoryPage() {
        return "category/category_form";
    }

    @PostMapping("/add/category")
    public String addCategory(@RequestParam String name,
                              @RequestParam String description) {
        categoryService.addCategory(name, description);
        return "redirect:/category";
    }

    @GetMapping("/edit/category/{id}")
    public String getEditCategoryPage(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "category/category_form";
    }

    @PostMapping("/edit/category/{id}")
    public String editCategory(@PathVariable Integer id,
                               @RequestParam String name,
                               @RequestParam String description) {
        categoryService.editCategory(id, name, description);
        return "redirect:/category";
    }

    @PostMapping("/delete/category/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}
