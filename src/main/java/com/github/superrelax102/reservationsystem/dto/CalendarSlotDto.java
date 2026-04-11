package com.github.superrelax102.reservationsystem.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarSlotDto {
    private LocalTime startTime;
    private boolean isAvailable;
    private String statusLabel;
}
