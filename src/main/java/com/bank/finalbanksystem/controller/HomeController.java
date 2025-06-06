package com.bank.finalbanksystem.controller;

import com.bank.finalbanksystem.dto.*;
import com.bank.finalbanksystem.entity.AccountDetail;
import com.bank.finalbanksystem.entity.AdminBank;
import com.bank.finalbanksystem.entity.Transaction;
import com.bank.finalbanksystem.entity.UserDetail;
import com.bank.finalbanksystem.repository.AccountRepo;
import com.bank.finalbanksystem.repository.Loginrepo;
import com.bank.finalbanksystem.repository.TransactionRepo;
import com.bank.finalbanksystem.repository.UserRepo;
import com.bank.finalbanksystem.service.ForgetPasswordService;
import com.bank.finalbanksystem.service.Services;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    Services services;
    @Autowired
    ForgetPasswordService forgetPasswordService;
    @GetMapping("/Admin-page/register")
    public String register(Model model)
    {
        model.addAttribute("user",new UserDto());
        return "registration";
    }
    @PostMapping("/Admin-page/register")
    public String register(@Valid @ModelAttribute("user")UserDto userDto, Errors errors, @RequestParam("photo")MultipartFile file,Model model,RedirectAttributes redirectAttributes) throws IOException, MessagingException {
       UserDetail userDetail1=userRepo.findByemail(userDto.getEmail());
        UserDetail aadh=userRepo.findByAadharno(userDto.getAadharno());
        UserDetail user=userRepo.findByUsername(userDto.getUsername());
        if(aadh!=null)
        {
            model.addAttribute("aaderror",true);
            return "registration";
        }
       if(userDetail1!=null)
       {
           model.addAttribute("mailerror",true);
           return "registration";
       }

       if(user!=null)
    {
        model.addAttribute("usererror",true);
        return "registration";
    }


        if(errors.hasErrors())
            return "registration";
        else
        {
            if (file.isEmpty()) {
                model.addAttribute("fileError", "Please upload a photo.");
                return "registration";
            }

            // Check file content type
            String contentType = file.getContentType();
            if (contentType == null ||
                    !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
                model.addAttribute("fileError", "Only JPG and PNG images are allowed.");
                return "registration";
            }

            // Check file size (max 5 MB)
            long maxSize = 5 * 1024 * 1024; // 5MB
            if (file.getSize() > maxSize) {
                model.addAttribute("fileError", "Image size must be less than 5MB.");
                return "registration";
            }





            if(LocalDateTime.now().getYear()-userDto.getDob().getYear()>=18  && LocalDateTime.now().getYear()-userDto.getDob().getYear()<=60 ) {
                UserDetail userDetail=services.save(userDto, file);

                forgetPasswordService.regemail(userDto.getEmail(),
                        "Welcome of BankofIndia",userDto.getFullname1(),userDetail.getAccountDetail().getId());
                redirectAttributes.addAttribute("success",true);
                return "redirect:/login";
            }
            else{
                model.addAttribute("error",true);
                return "registration";
            }
        }


    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("Admin-page")
    public String admin(Model model, Principal principal) throws MessagingException, UnsupportedEncodingException {
        UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user",userDetails);
      //  forgetPasswordService.regemail(userDetails.getUsername(),"Login","ADMIN",1921);
        AdminBank adminBank=services.admindash();
        model.addAttribute("bank",adminBank);
        return "Admin";
    }
    @GetMapping("user-page")
    public String user1(Model model,Principal principal) {
       System.out.println("priciple:  "+principal.getName());
        // UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
        // model.addAttribute("user",userDetails);
        UserDetail userDetail = userRepo.findByemail(principal.getName());
        System.out.println("accountdetail:  "+(userDetail.getAccountDetail()).getId());
        model.addAttribute("user", userDetail);
        model.addAttribute("services", services);


        return "user";
    }
    @GetMapping("/Admin-page/deposit")
    public String deposit(Model model)
    {
        UserTransferDto userTransferDto=new UserTransferDto();
        model.addAttribute("depo",userTransferDto);
        return "deposit";
    }
    @PostMapping("/Admin-page/deposit")
    public String deposit(@ModelAttribute("depo")UserTransferDto userTransferDto, Model model, RedirectAttributes redirectAttributes)
    {
        String acc=""+userTransferDto.getToId();
        Long mid=Long.parseLong(acc.substring(6));
        System.out.println(mid);
        AccountDetail accountDetail=accountRepo.findByid(mid);
        if(accountDetail==null)
        {
           model.addAttribute("error",true) ;
           return "deposit";
        }
        services.addAmount(mid, userTransferDto.getAmount());
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/Admin-page";
    }
    @GetMapping("/user-page/transfer")
    public String tranfer(Model model)
    {
        UserTransferDto userTransferDto=new UserTransferDto();
        model.addAttribute("transfer",userTransferDto);
        return "transaction";
    }
    @PostMapping("/user-page/transfer")
    public String tranfer(@ModelAttribute("transfer") UserTransferDto userTransferDto,Principal principal,Model model) throws Exception {
        UserDetail userDetail=userRepo.findByemail(principal.getName());
        System.out.println("get"+userTransferDto.getAmount());
        String acc=""+userTransferDto.getToId();
        Long accid=Long.parseLong(acc.substring(6));
        AccountDetail accountDetail=accountRepo.findByid(accid);
        if(accountDetail==null)
        {
         model.addAttribute("error",true);
         return "transaction";
        }
        else if(userDetail.getAccountDetail().getId()==userTransferDto.getToId())
        {
            model.addAttribute("error",true);
            return "transaction";
        }
        BigDecimal amount=(userDetail.getAccountDetail()).getAmount();
        if(amount.compareTo(userTransferDto.getAmount())<0)
        {
            model.addAttribute("error1",true);
            return "transaction";

        }
        services.transfer((userDetail.getAccountDetail()).getId(), accid, userTransferDto.getAmount());
        return "redirect:/user-page";
    }
    @GetMapping("/user-page/report")
    public String alltransaction(Model model)
    {
        UserTransact userTransact=new UserTransact();
        model.addAttribute("transact",userTransact);

        return "report";

    }
    @PostMapping("/user-page/report")
    public String alltransaction(@ModelAttribute("transact")UserTransact userTransact,Principal principal,Model model)
    {
        model.addAttribute("transact",userTransact);
        UserDetail userDetail=userRepo.findByemail(principal.getName());
        LocalDate from=userTransact.getFromdate();
        LocalDate to=userTransact.getTodate();
        if(from.getYear()== to.getYear() && from.getMonthValue()> to.getMonthValue())
        {
            model.addAttribute("error",true);
            return "report";
        }
        List<Transaction> transactions=services.transactionHistory(userTransact.getFromdate(),userTransact.getTodate(),
                (userDetail.getAccountDetail()).getId()) ;
           // System.out.println("transaction:" + transactions);

        model.addAttribute("transaction",transactions);
      return "report";

    }
    @GetMapping("/Admin-page/delete")
    public String delete(Model model)
    {
        DeleteUser deleteUser=new DeleteUser();
        model.addAttribute("del",deleteUser);
        return "delete";
    }
    @PostMapping("/Admin-page/delete")
    public String delete(@ModelAttribute("del")DeleteUser deleteUser,Model model,RedirectAttributes redirectAttributes)
    {
        String acc=""+deleteUser.getId();
        Long accno=Long.parseLong(acc.substring(6));
        AccountDetail accountDetail=accountRepo.findByid(accno);
        if(accountDetail==null)
        {
            model.addAttribute("error",true);
            return "delete";
        }
      services.delete(accno);
      redirectAttributes.addAttribute("delete",true);
      return "redirect:/Admin-page";
    }
    @GetMapping("/Admin-page/accounts")
    public String allaccount(Model model){
      List<AccountDetail> accountDetails=services.accounts();
      List<AllaccountDetail> allaccountDetails=new ArrayList<>();
      for(int i=0;i<accountDetails.size();i++)
      {
          UserDetail userDetail=userRepo.findByAccountDetail(accountDetails.get(i));
          AllaccountDetail allaccountDetail=new AllaccountDetail(accountDetails.get(i).getId(),userDetail.getFullname()
                  ,accountDetails.get(i).getDate(),accountDetails.get(i).getType(),accountDetails.get(i).getAmount());
          allaccountDetails.add(allaccountDetail);
      }
      //System.out.println(allaccountDetails);
      model.addAttribute("account",allaccountDetails);
      return "allaccount";
    }
    @GetMapping("/bankofindia")
    public String index()
    {
        return "index";
    }

}
