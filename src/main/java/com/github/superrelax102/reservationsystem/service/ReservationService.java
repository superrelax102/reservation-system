package com.github.superrelax102.reservationsystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.github.superrelax102.reservationsystem.dto.CalendarDayDto;
import com.github.superrelax102.reservationsystem.dto.CalendarSlotDto;
import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.dto.ReservationResponseDto;
import com.github.superrelax102.reservationsystem.entity.Reservation;
import com.github.superrelax102.reservationsystem.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MenuService menuService;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M月d日");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public List<CalendarDayDto> getCalendar(LocalDate today, Long menuid) {
        // 検索範囲の決定
        // 開始：今日の 00:00:00
        LocalDateTime startRange = today.atStartOfDay(); 
        // // 終了：14日後の 23:59:59.999...
        LocalDateTime endRange = today.plusDays(13).atTime(LocalTime.MAX);

        // 1. 判定用の「現在時刻」を取得しておく
        LocalDateTime now = LocalDateTime.now();

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
                    .filter(r -> "予約中".equals(r.getStatus()))
                    .toList();

            // 30分ずつ以下の処理を繰り返し
            LocalTime current = startTime;
            while (!current.isAfter(endTime)) {

                // 重なり判定
                // スロットの開始と終了を定義
                LocalTime slotStart = current;
                LocalTime slotEnd = current.plusMinutes(selectedDuration);

                // 過去かどうかの判定
                LocalDateTime slotDateTime = LocalDateTime.of(targetDate, slotStart);
                boolean isPast = slotDateTime.isBefore(now);    

                // その日の予約（dailyReservations）の中に、このスロットと重なるものが1つでもあるかチェック
                boolean isReserved = dailyReservations.stream().anyMatch(res -> {
                    LocalTime resStart = res.getStartat().toLocalTime();
                    LocalTime resEnd = res.getEndat().toLocalTime();
                    return resStart.isBefore(slotEnd) && resEnd.isAfter(slotStart);
                });

                slots.add(CalendarSlotDto.builder()
                    .startTime(current)
                    .isAvailable(!isReserved && !isPast)
                    .statusLabel(isReserved || isPast ? "×" : "○")
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

    @Transactional
    public void saveReservation(String username, LocalDate date, LocalTime time, Long menuid) {
        Reservation reservation = new Reservation();
        // idを仮設定
        reservation.setStartat(LocalDateTime.of(date, time));
        reservation.setEndat(LocalDateTime.of(date, time.plusMinutes(menuService.getDurationById(menuid))));
        reservation.setStatus("予約中");
        reservation.setUserid((long)1);
        reservation.setStaffid((long)1);
        reservation.setMenuid(menuid);
        reservation.setBillingfee(menuService.getbillingfeeById(menuid));
        reservation.setCreatedat(LocalDateTime.now());
        reservation.setIsdeleted(false);
        
        // リポジトリ経由で保存
        reservationRepository.save(reservation);
    }
    
    @Transactional
    public void cancelReservation(Long reservationid) {
        Reservation reservation = reservationRepository.findById(reservationid)
            .orElseThrow(() -> new RuntimeException("予約が見つかりません。ID: " + reservationid));
        reservation.setStatus("キャンセル");
    }


    public List<ReservationResponseDto> getMyReservations(Long userid) {
        List<Reservation> reservations = reservationRepository.findByUserid(userid);
        List<ReservationResponseDto> dtos = reservations.stream()
                                            .map(this::convertToDto)
                                            .toList();
        return dtos;
    }

    private ReservationResponseDto convertToDto(Reservation entity) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(entity.getId());
        // LocalDateTimeからフォーマット
        String reservationdate = entity.getStartat().format(DATE_FORMAT); // 〇月〇日
        String startTime = entity.getStartat().format(TIME_FORMAT); // 〇〇:〇〇
        String endTime = entity.getEndat().format(TIME_FORMAT); // ××:××
        
        // 結合してセット
        dto.setDisplaydatetime(String.format("%s　%s～%s", reservationdate, startTime, endTime));
        
        dto.setStatus(entity.getStatus());
        dto.setStaffname("ひろと");
        dto.setMenuname(menuService.getMenuById(entity.getMenuid()).getName());
        dto.setBillingfee(entity.getBillingfee());

        String createdDate = entity.getCreatedat().format(DATE_FORMAT); // 〇月〇日
        String createdTime = entity.getCreatedat().format(TIME_FORMAT); // 〇〇:〇〇

        dto.setDisplaycreatedtime(String.format("%s　%s", createdDate, createdTime));
        return dto;
    }

    public ReservationResponseDto getMyReservationById(Long reservationid) {
        Reservation reservation = reservationRepository.findById(reservationid)
            .orElseThrow(() -> new RuntimeException("予約が見つかりません。ID: " + reservationid));
        ReservationResponseDto dto = convertToDto(reservation);
        return dto;
    }



}
