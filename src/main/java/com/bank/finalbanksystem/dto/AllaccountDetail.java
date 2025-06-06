package com.bank.finalbanksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
public class AllaccountDetail {
    private Long id;
    private String name;
    private Date date;
    private String type;
    private BigDecimal amount;
}
