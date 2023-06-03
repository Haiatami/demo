package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String showAllCategories(Model model){
        List<Category>categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list";
    }
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category updatedCategory,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", updatedCategory);
            return "category/edit";
        }

        Category category = categoryService.getCategoryById(id);
        category.setName(updatedCategory.getName());
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
