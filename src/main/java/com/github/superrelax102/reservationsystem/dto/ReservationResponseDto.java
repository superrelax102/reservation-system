package com.github.superrelax102.reservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long id;
    private String displaydatetime;
    private String status;
    private String staffname;
    private String menuname;
    private int billingfee;
    private String displaycreatedtime;
}
