package com.bank.finalbanksystem.repository;

import com.bank.finalbanksystem.entity.AccountDetail;
import com.bank.finalbanksystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction> findByaccount_id(Long id);
    List<Transaction> findByAccount(AccountDetail accountDetail);
}
