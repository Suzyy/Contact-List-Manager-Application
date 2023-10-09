package com.example.clmp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.clmp.entity.User;
import com.example.clmp.repo.UserRepo;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        Collection<String> mappedAuthorities = Arrays.asList(user.getRole().split(","));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mappedAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
    
}
