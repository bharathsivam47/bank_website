package com.bank.finalbanksystem.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="account_detail")
@Getter
@Setter
@NoArgsConstructor
public class AccountDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private BigDecimal amount;
    private String type;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public AccountDetail( Date date, BigDecimal amount, String type,
                         List<Transaction> transactions) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.transactions = transactions;
    }


}
