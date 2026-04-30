package com.github.superrelax102.reservationsystem.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.github.superrelax102.reservationsystem.dto.CalendarDayDto;
import com.github.superrelax102.reservationsystem.dto.LoginUserDto;
import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.dto.ReservationResponseDto;
import com.github.superrelax102.reservationsystem.service.MenuService;
import com.github.superrelax102.reservationsystem.service.ReservationService;
import com.github.superrelax102.reservationsystem.service.UserSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MenuService menuService;
    private final UserSessionService userSessionService;


    @GetMapping("/set-calendar")
    public String setCalendar(HttpSession session, @RequestParam Long menuid, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();
        Long userid = user.getUserid();
        model.addAttribute("userid", userid);
        model.addAttribute("menuid", menuid);

        MenuResponseDto menuResponseDto = menuService.getMenuById(menuid);
        
        model.addAttribute("menuname", menuResponseDto.getName());
    

        LocalDate today = LocalDate.now();
        List<CalendarDayDto> calendar = reservationService.getCalendar(today, menuid);
        model.addAttribute("calendar", calendar);
        
        return "setCalendar";
    }

    @GetMapping("/confirm-regist-reservation")
    public String confirmRegistReservation(HttpSession session, @RequestParam LocalDate date, LocalTime time, Long menuid, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();
        Long userid = user.getUserid();
        model.addAttribute("userid", userid);
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("menuid", menuid);

        MenuResponseDto menuResponseDto = menuService.getMenuById(menuid);
        model.addAttribute("menuname", menuResponseDto.getName());


        return "confirmRegistReservation";
    }

    @PostMapping("/regist-reservation")
    public String registReservation(@RequestParam LocalDate date, LocalTime time, Long menuid, Model model) {
        reservationService.saveReservation(date, time, menuid);
        return "redirect:/complete-reservation";
    }


    @GetMapping("/complete-reservation")
    public String completeReservation() {       
        return "completeReservation";
    }

    @GetMapping("/reservations")
    public String getResevations(HttpSession session, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();
        Long userid = user.getUserid();
        model.addAttribute("userid", userid);
        List<ReservationResponseDto> reservations = reservationService.getMyReservations(userid);
        model.addAttribute("reservations", reservations);

        
        return "reservations";
    }    

    @GetMapping("/reservations/{reservationid}/cancel")
    public String confirmCancelResevation(@PathVariable Long reservationid, HttpSession session, Model model) {
        LoginUserDto user = userSessionService.getLoginUser();
        Long userid = user.getUserid();
        model.addAttribute("userid", userid);

        ReservationResponseDto reservation = reservationService.getMyReservationById(reservationid);
        model.addAttribute("reservation", reservation);
        return "confirmCancelReservation";
    }

    @PostMapping("/reservations/{reservationid}/cancel")
    public String cancelReservation(@PathVariable Long reservationid) {
        reservationService.cancelReservation(reservationid);
        return "redirect:/complete-reservation";
    }
}
