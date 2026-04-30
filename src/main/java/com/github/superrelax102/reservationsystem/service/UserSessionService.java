package com.github.superrelax102.reservationsystem.service;

import com.github.superrelax102.reservationsystem.dto.LoginUserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {
    @Autowired
    private HttpSession session;

    public LoginUserDto getLoginUser() {
        return (LoginUserDto) session.getAttribute("LOGIN_USER");
    }

}
