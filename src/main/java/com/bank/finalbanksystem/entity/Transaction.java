package com.bank.finalbanksystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="Transaction_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactid;
    private String type;
    private LocalTime time;
    private BigDecimal amount;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name="account_id")
    private AccountDetail account;

    public Transaction( String type, LocalTime time, BigDecimal amount,LocalDate date, AccountDetail account) {
        this.type = type;
        this.time = time;
        this.amount = amount;
        this.date = date;
        this.account = account;
    }

}
