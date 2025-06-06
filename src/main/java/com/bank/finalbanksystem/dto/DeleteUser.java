package com.bank.finalbanksystem.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DeleteUser {
    @Pattern(regexp = "^[0-9]$",message = "Account no number")
    private Long id;
}
