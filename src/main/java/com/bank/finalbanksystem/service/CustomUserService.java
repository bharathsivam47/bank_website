package com.bank.finalbanksystem.service;

import com.bank.finalbanksystem.entity.Login;
import com.bank.finalbanksystem.repository.Loginrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    private Loginrepo loginrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login log=loginrepo.findByemail(username);
        if(log==null)
        {
            throw new UsernameNotFoundException("UserName Not Found Exception");
        }

        return  new CustomUser(log);

    }
}
