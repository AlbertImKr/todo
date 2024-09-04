package me.albert.todo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.repository.AccountRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    public static final String USERNAME_IS_EXISTED = "이미 존재하는 유저 이름입니다.";

    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public void register(String username, String password) {
        if (accountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(USERNAME_IS_EXISTED);
        }
        accountRepository.save(new Account(username, password));
    }
}
