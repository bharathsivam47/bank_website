package com.bank.finalbanksystem.repository;

import com.bank.finalbanksystem.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Loginrepo extends JpaRepository<Login,Long> {
    Login findByemail(String email);
}
