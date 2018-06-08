package com.mir.newgift.controllers;

import com.mir.newgift.core.GiftSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class AdminController {

    @Autowired
    private GiftSetService giftSetService;

    @GetMapping("/gifts/dashboard")
    public String showMainPage () {
        return "admin-dashboard";
    }

    @GetMapping("/gifts/dashboard/update")
    public String updateData() throws SQLException {
        giftSetService.main();
        return "done";
    }
}
