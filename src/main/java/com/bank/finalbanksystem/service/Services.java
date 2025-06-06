package com.bank.finalbanksystem.service;


import com.bank.finalbanksystem.dto.UserDto;
import com.bank.finalbanksystem.entity.AccountDetail;
import com.bank.finalbanksystem.entity.AdminBank;
import com.bank.finalbanksystem.entity.Transaction;
import com.bank.finalbanksystem.entity.UserDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface Services {
    UserDetail save(UserDto userDto, MultipartFile file) throws IOException;
    BigDecimal checkBalance(Long id);
    void addAmount(Long id,BigDecimal amount);
    void transfer(Long fromid,Long toid,BigDecimal amount) throws Exception;

    List<Transaction> transactionHistory(LocalDate from, LocalDate to, Long id);
    AdminBank admindash();
    void delete(Long id);
    List<AccountDetail> accounts();


}
