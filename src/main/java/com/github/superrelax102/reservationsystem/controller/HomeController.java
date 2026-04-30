package com.github.superrelax102.reservationsystem.controller;

import com.github.superrelax102.reservationsystem.dto.LoginUserDto;
import com.github.superrelax102.reservationsystem.service.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class HomeController {
 
    private final UserSessionService userSessionService;

    @PostMapping("/usertop")
    public String userTopLogin(@RequestParam String userid, HttpSession session) {
        try {
            Long id = Long.valueOf(userid);
            LoginUserDto loginuser = new LoginUserDto();
            loginuser.setUserid(id);

            session.setAttribute("LOGIN_USER", loginuser);

            return "redirect:/home"; 
        } catch (NumberFormatException e) {
            // 数字以外が来た場合は、ログイン画面に戻すなどの処理
            return "login";
        }
    }

    @GetMapping("/usertop")
    public String getMethodName(HttpSession session, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();
        Long userid = user.getUserid();
        model.addAttribute("userid", userid);
        return "home";
    }
    

    @GetMapping("/home")
    public String userHome(HttpSession session, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();

        if (user == null) {
                return "redirect:/login"; 
            }

        Long userid = user.getUserid();
        model.addAttribute("userid", userid);

        return "home";
    }
    
}
