package com.github.superrelax102.reservationsystem.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.github.superrelax102.reservationsystem.dto.CalendarDayDto;
import com.github.superrelax102.reservationsystem.dto.CalendarSlotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    // private final ReservationRepository reservationRepository;

    public List<CalendarDayDto> getCalendar(LocalDate today) {
        // 1. 検索範囲の決定
        // 開始：今日の 00:00:00
        // LocalDateTime startRange = today.atStartOfDay(); 
        // // 終了：14日後の 23:59:59.999...
        // LocalDateTime endRange = today.plusDays(13).atTime(LocalTime.MAX);

        // 2. DBから該当期間の予約をすべて取得
        // List<Reservation> allReservations = reservationRepository
        //             .findByStartAtBetweenOrderByStartAtAsc(startRange, endRange);

        // 3. 14日間分のカレンダー枠を生成するループ
        List<CalendarDayDto> days = new ArrayList<>();

        // 開始時間と終了時間を定義（9:00〜18:00）
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        for (int i = 0; i < 14; i++) {
            LocalDate targetDate = today.plusDays(i);
            List<CalendarSlotDto> slots = new ArrayList<>();
            // その日の予約だけをフィルタリング
            // List<Reservation> dailyReservations = allReservations.stream()
            //         .filter(r -> r.getStartAt().toLocalDate().equals(targetDate))
            //         .toList();


            LocalTime current = startTime;
            while (!current.isAfter(endTime)) {

                // ↓重なり判定未済
                slots.add(CalendarSlotDto.builder()
                    .startTime(current)
                    .isAvailable(true) // 仮で全て空き
                    .statusLabel("○")
                    .build());
                current = current.plusMinutes(30);
            }
            days.add(CalendarDayDto.builder()
                .date(targetDate)
                .slots(slots)
                .build());

            // days.add(buildDayDto(targetDate, dailyReservations));
        }


        return days;
    }



}
