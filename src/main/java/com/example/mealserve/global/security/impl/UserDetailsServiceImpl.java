package com.example.mealserve.global.security.impl;

import com.example.mealserve.domain.account.entity.Account;
import com.example.mealserve.domain.account.AccountRepository;
import com.example.mealserve.global.exception.CustomException;
import com.example.mealserve.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL_PASSWORD));

        return new UserDetailsImpl(account);
    }
}