package com.example.clmp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

//enable h2 database console
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepo userRepo
    //@Override
    //protected void configure(HttpSecurity httpSecurity) throws Exception {
    //    httpSecurity.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/console/**").permitAll();

    //    httpSecurity.csrf().disable();
    //    httpSecurity.headers().frameOptions().disable();
    //}

    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    //TODO
    //}

    //@Override void configure(HttpSecurity http) throws Exception {
    //    //TODO
    //}
    
}
