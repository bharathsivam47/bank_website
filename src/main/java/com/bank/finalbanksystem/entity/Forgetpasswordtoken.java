package com.bank.finalbanksystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Forgetpasswordtoken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;
    @ManyToOne(targetEntity = Login.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private Login login;
    @Column(nullable = false)
    private LocalDateTime expireTime;
    @Column(nullable = false)
    private boolean isUsed;

}
