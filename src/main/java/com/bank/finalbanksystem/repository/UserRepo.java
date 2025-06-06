package com.bank.finalbanksystem.repository;

import com.bank.finalbanksystem.entity.AccountDetail;
import com.bank.finalbanksystem.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDetail,Long> {
    UserDetail findByemail(String email);
    UserDetail findByAccountDetail(AccountDetail accountDetail);
    UserDetail findByAadharno(String aadharno);
    UserDetail findByUsername(String aadharno);
}

