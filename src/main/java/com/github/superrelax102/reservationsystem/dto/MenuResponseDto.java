package com.github.superrelax102.reservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponseDto {
    private Long id;
    private String name;
    private String detail;
    private Integer duration; 
    private Integer price;
    private String formattedDuration;
    private String formattedPrice;
}
