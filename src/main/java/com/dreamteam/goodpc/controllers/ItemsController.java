package com.dreamteam.goodpc.controllers;

import com.dreamteam.goodpc.data.CategoryRepository;
import com.dreamteam.goodpc.data.ComponentRepository;
import com.dreamteam.goodpc.data.ItemRepository;
import com.dreamteam.goodpc.entities.Category;
import com.dreamteam.goodpc.entities.Component;
import com.dreamteam.goodpc.entities.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
@SessionAttributes("basket")
public class ItemsController {
    private ItemRepository itemRepo;

    public ItemsController(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @ModelAttribute("basket")
    public List<Item> basket() {
        return new ArrayList<>();
    }

    @GetMapping("/{id:\\d+}")
    public String getItem(@PathVariable("id") Item item, Model model) {
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/{category:[a-z-]+}")
    public String getItemsByCategory(@PathVariable("category") String category, Model model) {
        List<Item> items = itemRepo.findByCategoryName(category);
        log.info("items: {}", items);
        model.addAttribute("items", items);
        model.addAttribute("category", category);
        log.info("returning {} items", category);
        return "items";
    }




}
