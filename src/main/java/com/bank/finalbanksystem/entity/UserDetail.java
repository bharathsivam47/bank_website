package com.bank.finalbanksystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="user_detail")
@NoArgsConstructor
@Getter
@Setter
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String fathername;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    private String address;
    @Column(nullable = false,unique = true)
    private String aadharno;
    private String phoneno;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account")
    private AccountDetail accountDetail;

    public UserDetail(String fullname, String gender, String fathername, LocalDate date, String username, String email,
                      String address, String aadharno, String phoneno, String photo, AccountDetail accountDetail) {
        this.fullname = fullname;
        this.gender = gender;
        this.fathername = fathername;
        this.date = date;
        this.username = username;
        this.email = email;
        this.address = address;
        this.aadharno = aadharno;
        this.phoneno = phoneno;
        this.photo = photo;
        this.accountDetail = accountDetail;
    }



}
