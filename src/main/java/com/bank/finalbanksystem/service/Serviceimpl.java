package com.bank.finalbanksystem.service;

import com.bank.finalbanksystem.dto.UserDto;
import com.bank.finalbanksystem.entity.*;
import com.bank.finalbanksystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class Serviceimpl implements Services  {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    Loginrepo loginrepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ForgetRepo forgetRepo;


    @Override
    public UserDetail save(UserDto userDto, MultipartFile file) throws IOException {
        AccountDetail accountDetail=new AccountDetail(new Date(), BigDecimal.ZERO,"SB", new ArrayList<Transaction>());

       UserDetail user = new UserDetail(userDto.getFullname1(), userDto.getGender(), userDto.getFathername(),userDto.getDob(),
               userDto.getUsername(), userDto.getEmail(), userDto.getAddress(), userDto.getAadharno(), userDto.getPhoneno(),
               Base64.getEncoder().encodeToString(file.getBytes()),accountDetail);

       Login login=new Login(userDto.getEmail(), passwordEncoder.encode(userDto.getUsername()), "USER", userDto.getUsername());


        userRepo.save(user);
        accountRepo.save(accountDetail);
        loginrepo.save(login);
        return user;

    }

    @Override
    public BigDecimal checkBalance(Long id) {
        AccountDetail accountDetail=accountRepo.findByid(id);

        return accountDetail.getAmount();
    }

    @Override
    public void addAmount(Long id, BigDecimal amount) {
        AccountDetail accountDetail=accountRepo.findByid(id);
        BigDecimal am=accountDetail.getAmount().add(amount);
        accountDetail.setAmount(am);
        accountRepo.save(accountDetail);
        //transaction detail
        Transaction transaction=new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTime(LocalTime.now());
        transaction.setDate(LocalDate.now());
        transaction.setAccount(accountDetail);
        transactionRepo.save(transaction);

    }

    @Override
    public void transfer(Long fromid, Long toid, BigDecimal amount) throws Exception {
        AccountDetail fromaccount=accountRepo.findByid(fromid);
        AccountDetail toaccount=accountRepo.findByid(toid);
        System.out.println("from:"+fromaccount.getAmount());
        System.out.println("to:"+amount);
        if(fromaccount.getAmount().compareTo(amount)>0)
        {
            fromaccount.setAmount(fromaccount.getAmount().subtract(amount));
            accountRepo.save(fromaccount);
            toaccount.setAmount(toaccount.getAmount().add(amount));
            accountRepo.save(toaccount);
            //Transcation for from account
            Transaction transaction=new Transaction("TRANSFER To   "+(toaccount.getId()), LocalTime.now(),amount, LocalDate.now(),fromaccount);
            transactionRepo.save(transaction);
            Transaction transaction1=new Transaction("CREDIT From   "+(fromaccount.getId()),LocalTime.now(),amount,LocalDate.now(),toaccount);
            transactionRepo.save(transaction1);
        }
        else{
            throw new RuntimeException("INVALID AMOUNT");
        }

    }

   @Override
   public List<Transaction> transactionHistory(LocalDate from, LocalDate to, Long id) {
       AccountDetail accountDetail = accountRepo.findByid(id);
       List<Transaction> transactions = transactionRepo.findByAccount(accountDetail);

       // Ensure from is before or equal to to
       if (from.isAfter(to)) {
           throw new IllegalArgumentException("From date must be before or equal to To date.");
       }

       List<Transaction> filteredTransactions = new ArrayList<>();
       for (Transaction t : transactions) {
           LocalDate date = t.getDate();
           if ((date.isEqual(from) || date.isAfter(from)) && (date.isEqual(to) || date.isBefore(to))) {
               filteredTransactions.add(t);
           }
       }

       return filteredTransactions;
   }


    @Override
    public AdminBank admindash() {
        List<AccountDetail> accountDetails=accountRepo.findAll();
        int count=accountDetails.size();
        BigDecimal totalamount=BigDecimal.ZERO;
        for(int i=0;i<count;i++)
        {
            AccountDetail accountDetail=accountDetails.get(i);
           totalamount=totalamount.add(accountDetail.getAmount()) ;
        }
        AdminBank adminBank=new AdminBank(count,totalamount);
        return adminBank;
    }

    @Override
    public void  delete(Long id) {

        AccountDetail accountDetail=accountRepo.findByid(id);
        UserDetail userDetail=userRepo.findByAccountDetail(accountDetail);
        System.out.println("Email:"+userDetail.getEmail());
        Login login=loginrepo.findByemail(userDetail.getEmail());
        System.out.println("login:"+login);
        System.out.println("username:"+userDetail);
        System.out.println("accountDetail:"+accountDetail);
        List<Transaction> transactions=transactionRepo.findByAccount(accountDetail);
        List<Forgetpasswordtoken> forgetpasswordtokens=forgetRepo.findByLogin(login);
        forgetRepo.deleteAll(forgetpasswordtokens);
        transactionRepo.deleteAll(transactions);
        accountRepo.delete(accountDetail);
        userRepo.delete(userDetail);
        loginrepo.delete(login);


    }

    @Override
    public List<AccountDetail> accounts() {
        return accountRepo.findAll();
    }

}
