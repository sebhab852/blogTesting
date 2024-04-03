package com.restblog.blog.services;

import com.restblog.blog.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public MyUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountService.findByEmail(username);
        if(optionalAccount.isEmpty()){
            throw new UsernameNotFoundException("Account nicht gefunden!");
        }

        Account acc = optionalAccount.get();

        List<GrantedAuthority> grantedAuthorities = acc
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(acc.getEmail(), acc.getPassword(), grantedAuthorities);
    }
}
