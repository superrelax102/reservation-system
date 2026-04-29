package com.github.superrelax102.reservationsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/usertop")
    public String userTop(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "top";
    }
}
