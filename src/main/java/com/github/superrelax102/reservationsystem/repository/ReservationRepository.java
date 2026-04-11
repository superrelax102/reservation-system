package com.github.superrelax102.reservationsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.superrelax102.reservationsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 指定した期間（startからendまで）の予約を、開始日時の昇順で取得する
    List<Reservation> findByStartAtBetweenOrderByStartAtAsc(LocalDateTime start, LocalDateTime end);
}
