package com.github.superrelax102.reservationsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ReservationController {

    @PostMapping("/set-calendar")
    public String setCalendar(@RequestParam String username, long id, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        
        return "setCalendar";
    }
    
}
