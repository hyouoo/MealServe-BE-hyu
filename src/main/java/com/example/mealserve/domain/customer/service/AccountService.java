package com.example.mealserve.domain.customer.service;

import com.example.mealserve.domain.customer.dto.AccountJoinRequestDto;
import com.example.mealserve.domain.customer.entity.Account;
import com.example.mealserve.domain.customer.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    @Transactional
    public void registerNewAccount(AccountJoinRequestDto requestDto) {
        Account account = requestDto.toEntity();
        accountRepository.save(account);
    }
}
