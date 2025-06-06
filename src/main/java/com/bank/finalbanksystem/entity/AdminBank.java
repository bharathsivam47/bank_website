package com.bank.finalbanksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class AdminBank {
    private int totalaccount;
    private BigDecimal totalmoney;

    public void setTotalaccount(int totalaccount) {
        this.totalaccount = totalaccount;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
    }
}
