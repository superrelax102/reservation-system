package com.github.superrelax102.reservation_system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.superrelax102.reservation_system.entity.*;
;


@Controller
public class ReservationController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/usertop")
    public String userTop(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "top";
    }

    @PostMapping("/select-menu")
    public String selectMenu(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        
		//--テストデータ--
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "カット", "キャッチ１", 60, 3000));
		list.add(new Menu(2, "パーマ", "キャッチ２", 120, 9000));
		list.add(new Menu(3, "ヘッドスパ", "キャッチ３", 30, 5000));
		//--テストデータ--
		
		model.addAttribute("menuList", list); 
        return "selectMenu";
    }

    @PostMapping("/set-calendar")
    public String setCalendar(@RequestParam String username, long menuId, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("menuId", menuId);
        
        return "setCalendar";
    }
    
}
