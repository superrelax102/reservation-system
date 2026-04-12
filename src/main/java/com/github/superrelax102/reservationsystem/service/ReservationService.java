package com.github.superrelax102.reservationsystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.github.superrelax102.reservationsystem.dto.CalendarDayDto;
import com.github.superrelax102.reservationsystem.dto.CalendarSlotDto;
import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.entity.Reservation;
import com.github.superrelax102.reservationsystem.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MenuService menuService;

    public List<CalendarDayDto> getCalendar(LocalDate today, Long menuid) {
        // 検索範囲の決定
        // 開始：今日の 00:00:00
        LocalDateTime startRange = today.atStartOfDay(); 
        // // 終了：14日後の 23:59:59.999...
        LocalDateTime endRange = today.plusDays(13).atTime(LocalTime.MAX);

        // ReservationDBから該当期間の予約をすべて取得
        List<Reservation> allReservations = reservationRepository
                    .findByStartatBetweenOrderByStartatAsc(startRange, endRange);

        // MenuDBから該当のメニュー情報を取得し、所要時間を取得
        MenuResponseDto selectedMenu = menuService.getMenuById(menuid);
        Integer selectedDuration = selectedMenu.getDuration();

        // 14日間分のカレンダー枠を生成するループ
        List<CalendarDayDto> days = new ArrayList<>();

        // 開始時間と終了時間を定義（9:00〜18:00）
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        for (int i = 0; i < 14; i++) {
            LocalDate targetDate = today.plusDays(i);
            List<CalendarSlotDto> slots = new ArrayList<>();
            // その日の予約だけをフィルタリング
            List<Reservation> dailyReservations = allReservations.stream()
                    .filter(r -> r.getStartat().toLocalDate().equals(targetDate))
                    .toList();

            // 30分ずつ以下の処理を繰り返し
            LocalTime current = startTime;
            while (!current.isAfter(endTime)) {

                // 重なり判定
                // スロットの開始と終了を定義
                LocalTime slotStart = current;
                LocalTime slotEnd = current.plusMinutes(selectedDuration);

                // その日の予約（dailyReservations）の中に、このスロットと重なるものが1つでもあるかチェック
                boolean isReserved = dailyReservations.stream().anyMatch(res -> {
                    LocalTime resStart = res.getStartat().toLocalTime();
                    LocalTime resEnd = res.getEndat().toLocalTime();
                    return resStart.isBefore(slotEnd) && resEnd.isAfter(slotStart);
                });

                slots.add(CalendarSlotDto.builder()
                    .startTime(current)
                    .isAvailable(!isReserved)
                    .statusLabel(isReserved ? "×" : "○")
                    .build());
                current = current.plusMinutes(30);
            }
            days.add(CalendarDayDto.builder()
                .date(targetDate)
                .slots(slots)
                .build());

        }


        return days;
    }



}
