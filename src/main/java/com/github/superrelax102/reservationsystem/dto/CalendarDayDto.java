package com.github.superrelax102.reservationsystem.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarDayDto {
    private LocalDate date;
    private List<CalendarSlotDto> slots;
}
