package com.github.superrelax102.reservation_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @PostMapping("/usertop")
    public String userTop(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "top";
    }
}
