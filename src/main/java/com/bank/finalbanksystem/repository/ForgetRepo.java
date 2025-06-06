package com.bank.finalbanksystem.repository;

import com.bank.finalbanksystem.entity.Forgetpasswordtoken;
import com.bank.finalbanksystem.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForgetRepo extends JpaRepository<Forgetpasswordtoken,Long> {
    Forgetpasswordtoken findByToken(String token);
    List<Forgetpasswordtoken> findByLogin(Login login);
}
