package com.dreamteam.goodpc.controllers;

import com.dreamteam.goodpc.data.CategoryRepository;
import com.dreamteam.goodpc.entities.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/categories")
public class CategoriesController {
    private CategoryRepository categoryRepository;

    public CategoriesController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{name}")
    public String getCategory(@PathVariable("name") String name, Model model) {
        Category components = categoryRepository.findByName(name).orElseThrow();
        model.addAttribute("categories", components.getSubCategories());
        model.addAttribute("name", name);
        log.info("returned category " + name);
        return "categories";
    }


}
