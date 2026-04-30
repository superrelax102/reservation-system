package com.github.superrelax102.reservationsystem.controller;

import java.util.List;

import com.github.superrelax102.reservationsystem.dto.LoginUserDto;
import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.service.MenuService;
import com.github.superrelax102.reservationsystem.service.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;
    private final UserSessionService userSessionService;

	@GetMapping("/select-menu")
    public String selectMenu(HttpSession session, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();
        Long userid = user.getUserid();
        model.addAttribute("userid", userid);
        
		List<MenuResponseDto> list = menuService.getAllMenus();
		
		model.addAttribute("menuList", list); 
        return "selectMenu";
    }
}
