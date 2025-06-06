package com.bank.finalbanksystem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String fullname1;
    private String username;
    private String gender;
    private String email;
    private String address;
    private String aadharno;
    private String phoneno;
    private MultipartFile photo;
    private String fathername;
    private LocalDate dob;


}
