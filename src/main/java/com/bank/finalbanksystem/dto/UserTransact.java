package com.bank.finalbanksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTransact {
    private Long id;
    private LocalDate fromdate;
    private LocalDate todate;
}
