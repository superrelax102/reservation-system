package com.github.superrelax102.reservationsystem.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.github.superrelax102.reservationsystem.dto.CalendarDayDto;
import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.dto.ReservationResponseDto;
import com.github.superrelax102.reservationsystem.service.MenuService;
import com.github.superrelax102.reservationsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MenuService menuService;

    @PostMapping("/set-calendar")
    public String setCalendar(@RequestParam String username, Long menuid, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("menuid", menuid);

        MenuResponseDto menuResponseDto = menuService.getMenuById(menuid);
        
        model.addAttribute("menuname", menuResponseDto.getName());
    

        LocalDate today = LocalDate.now();
        List<CalendarDayDto> calendar = reservationService.getCalendar(today, menuid);
        model.addAttribute("calendar", calendar);
        
        return "setCalendar";
    }

    @PostMapping("/confirm-reservation")
    public String confirmReservation(@RequestParam String username, LocalDate date, LocalTime time, Long menuid, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("menuid", menuid);

        MenuResponseDto menuResponseDto = menuService.getMenuById(menuid);
        model.addAttribute("menuname", menuResponseDto.getName());


        return "confirmReservation";
    }

    @PostMapping("/regist-reservation")
    public String registReservation(@RequestParam String username, LocalDate date, LocalTime time, Long menuid, Model model) {
        reservationService.saveReservation(username, date, time, menuid);
        return "redirect:/complete-reservation";
    }


    @GetMapping("/complete-reservation")
    public String completeReservation() {       
        return "completeReservation";
    }

    @PostMapping("/check-history")
    public String postMethodName(@RequestParam String username, Long userid, Model model) {
        model.addAttribute("username", username);
        List<ReservationResponseDto> reservations = reservationService.getMyReservations(userid);
        model.addAttribute("reservations", reservations);

        
        return "checkHistory";
    }    
}
