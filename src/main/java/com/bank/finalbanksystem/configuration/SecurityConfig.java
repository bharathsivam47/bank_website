package com.bank.finalbanksystem.configuration;

import com.bank.finalbanksystem.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
    {
        http.csrf(c->c.disable())
                .authorizeHttpRequests(request->
                        request
                                .requestMatchers("/login","/bankofindia","/css/**","/password-request","/reset-password").permitAll()
                                .requestMatchers("/Admin-page","/register").hasAuthority("ADMIN")
                                .requestMatchers("/user-page").hasAuthority("USER")
                                .anyRequest().authenticated()
                )
                .formLogin(form->form.loginPage("/login")
                        .loginProcessingUrl("/login").successHandler(customSuccessHandler).permitAll()
                )
                .logout(form->form.invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll()
                );
         return http.build();

    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());
    }
}
