package com.github.superrelax102.reservationsystem.controller;

import java.util.List;

import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;

	@PostMapping("/select-menu")
    public String selectMenu(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        
		List<MenuResponseDto> list = menuService.getAllMenus();
		
		model.addAttribute("menuList", list); 
        return "selectMenu";
    }
}
