package com.github.superrelax102.reservationsystem.controller;

import java.time.LocalDate;
import java.util.List;

import com.github.superrelax102.reservationsystem.dto.CalendarDayDto;
import com.github.superrelax102.reservationsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/set-calendar")
    public String setCalendar(@RequestParam String username, Long menuid, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("id", menuid);
    

        LocalDate today = LocalDate.now();
        List<CalendarDayDto> calendar = reservationService.getCalendar(today, menuid);
        model.addAttribute("calendar", calendar);
        
        return "setCalendar";
    }
    
}
