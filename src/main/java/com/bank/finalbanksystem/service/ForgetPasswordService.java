package com.bank.finalbanksystem.service;

import com.bank.finalbanksystem.entity.Forgetpasswordtoken;
import com.bank.finalbanksystem.repository.ForgetRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgetPasswordService {
    private final int minute=10;
    @Autowired
    private JavaMailSender javaMailSender;
    public String generate_Token()
    {
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expireTimeRange()
    {
        System.out.println(LocalDateTime.now().plusMinutes(minute));
        return LocalDateTime.now().plusMinutes(minute);
    }
    public void sendEmail(String to,String subject,String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        String emailcontent="<p> Hello </p>"+"click the link below to reset password"+
                "<p><a href=\"" + emailLink +"\"> change my password</a></p>" +"<br>"
            +"Ignore this message if you did not made this request";
        helper.setText(emailcontent,true);
        helper.setFrom("bankofindia1921@gmail.com","BankOfIndia");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
       // System.out.println("Message sent successfully");


    }
    public void regemail(String to,String subject,String name,long accno) throws MessagingException, UnsupportedEncodingException {
       MimeMessage message=javaMailSender.createMimeMessage();
       MimeMessageHelper messageHelper=new MimeMessageHelper(message);
       String emailcontent="<h1>WELCOME TO BANK OF INDIA !!!</h1>"+
               "<br>"
               +"<h2>Dear "+name+",</h2>"
               +"we're so glad you're here."
               +"Thank you for creating your account with us"
               +"Your account has been created successfully and enjoy your secure and convenient banking with us"
               +"<h2>Your Account Number :192147"+accno+"</h2>"
               +"you can login in your account by using email and create your password with forget password option"
               +"<br>"
               +"Welcome aboard"+"<br>"+"Bank of India team";

       messageHelper.setText(emailcontent,true);
       messageHelper.setFrom("bankofindia1921@gmail.com","BankOfIndia");
        messageHelper.setSubject(subject);
       messageHelper.setTo(to);
       javaMailSender.send(message);
       System.out.println("Message sent successfully");
    }
    public boolean isexpired(Forgetpasswordtoken forgetpasswordtoken)
    {
       return LocalDateTime.now().isAfter(forgetpasswordtoken.getExpireTime()) ;
    }
    public String checkValidity(Forgetpasswordtoken forgetpasswordtoken, Model model) {
        if (forgetpasswordtoken== null) {
            model.addAttribute("error", "Invalid token");
            return "error-page";
        } else if (forgetpasswordtoken.isUsed()) {
            model.addAttribute("error", "token is already used");
            return "error-page";

        } else if (isexpired(forgetpasswordtoken)) {
            model.addAttribute("error", "token expired");
            return "error-page";

        } else{
          //  model.addAttribute("success",true);
            return "reset-password";
        }
    }




}
