package com.bank.finalbanksystem.repository;

import com.bank.finalbanksystem.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountDetail,Long> {
    AccountDetail findByid(Long id);
}
