package com.mir.newgift.controllers;

import com.mir.newgift.core.CategoryRepository;
import com.mir.newgift.core.SimpleGiftSetRepository;
import com.mir.newgift.model.Category;
import com.mir.newgift.model.SimpleGiftSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    private SimpleGiftSetRepository giftSetRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/gifts/search")
    public String showMainPage(Model model) throws SQLException {
        categoryRepository.setCategories();
        ArrayList<Category> categories = categoryRepository.getCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/gifts/result")
    public String searchGiftSets(@RequestParam String gender, @RequestParam String age, @RequestParam String order,
                                 @RequestParam String category1, @RequestParam String category2, @RequestParam String category3,
                                 Model model) throws SQLException{
        giftSetRepository.setSimpleGiftSets(gender, age, order, category1, category2, category3);
        ArrayList<SimpleGiftSet> gs = giftSetRepository.getSimpleGiftSets();
        model.addAttribute("giftSets", gs);
        return "result";
    }
}
