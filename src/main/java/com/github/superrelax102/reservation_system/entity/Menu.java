package com.github.superrelax102.reservation_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    private long menuId;
    private String menuName;
    private String menuDetail;
    private int menuDuration;
    private int menuCurrentFee;

}
