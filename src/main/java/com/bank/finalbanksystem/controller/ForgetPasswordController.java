package com.bank.finalbanksystem.controller;

import com.bank.finalbanksystem.entity.Forgetpasswordtoken;
import com.bank.finalbanksystem.entity.Login;
import com.bank.finalbanksystem.repository.ForgetRepo;
import com.bank.finalbanksystem.repository.Loginrepo;
import com.bank.finalbanksystem.service.ForgetPasswordService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class ForgetPasswordController {
    @Autowired
    Loginrepo loginrepo;
    @Autowired
    ForgetPasswordService forgetPasswordService;
    @Autowired
    ForgetRepo forgetRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/password-request")
    public String password_request()
    {
        return "password-request";
    }
    @PostMapping("/password-request")
    public String savepasswordrequest(@RequestParam("username")String username, Model model)
    {
        Login login=loginrepo.findByemail(username);
        if(login==null)
        {
            model.addAttribute("error",true);
            return "password-request";
        }
        Forgetpasswordtoken forgetpasswordtoken=new Forgetpasswordtoken();
        forgetpasswordtoken.setExpireTime(forgetPasswordService.expireTimeRange());
        forgetpasswordtoken.setLogin(login);
        forgetpasswordtoken.setUsed(false);
        forgetpasswordtoken.setToken(forgetPasswordService.generate_Token());
        String emailLink="http://localhost:8080/reset-password?token="+forgetpasswordtoken.getToken();
        try {
            forgetPasswordService.sendEmail(login.getEmail(), "password reset link", emailLink);
        } catch (UnsupportedEncodingException|MessagingException e) {
            Model model1 = model.addAttribute("error", true);
            System.out.println("Message error forget");
            return "password_request";
        }
        forgetRepo.save(forgetpasswordtoken);
        return "redirect:/password-request?success";
    }
    @GetMapping("/reset-password")
    public String resetpassword(@Param(value="token") String token, HttpSession session,Model model)
    {
        session.setAttribute("token",token);
        Forgetpasswordtoken forgetpasswordtoken=forgetRepo.findByToken(token);
        System.out.println("token"+token);
         return forgetPasswordService.checkValidity(forgetpasswordtoken,model);
    }
    @PostMapping("/reset-password")
    public String saveresetpassword(HttpServletRequest request, HttpSession session, Model model)
    {
        String password=request.getParameter("password");
        String token=(String)session.getAttribute("token");
        Forgetpasswordtoken forgetpasswordtoken=forgetRepo.findByToken(token);
        Login login=forgetpasswordtoken.getLogin();
        login.setPassword(passwordEncoder.encode(password));
        loginrepo.save(login);
        forgetpasswordtoken.setUsed(true);
        forgetRepo.save(forgetpasswordtoken);
       // model.addAttribute("success1",true);
        return "redirect:/login?success1";
    }

}
