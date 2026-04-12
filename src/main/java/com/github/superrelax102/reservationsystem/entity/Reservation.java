package com.github.superrelax102.reservationsystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startat;
    private LocalDateTime endat;
    private String status;
    private Long userid;
    private Long staffid;
    private Long menuid;
    private int billingfee;
    private LocalDateTime createdat;
    private boolean isdeleted;
}
