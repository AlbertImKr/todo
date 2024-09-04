package me.albert.todo.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.repository.AccountRepository;
import me.albert.todo.service.dto.response.TokensResponse;
import me.albert.todo.service.exception.AuthenticationFailedException;
import me.albert.todo.utils.JwtTokenProvider;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    public static final String USERNAME_IS_EXISTED = "이미 존재하는 유저 이름입니다.";
    public static final String USERNAME_OR_PASSWORD_NOT_MATCHED = "유저 이름 또는 비밀번호가 일치하지 않습니다.";

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public void register(String username, String password) {
        if (accountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(USERNAME_IS_EXISTED);
        }
        accountRepository.save(new Account(username, password));
    }

    @Override
    public TokensResponse login(String username, String password) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException(USERNAME_OR_PASSWORD_NOT_MATCHED));
        if (!account.matchPassword(password)) {
            throw new AuthenticationFailedException(USERNAME_OR_PASSWORD_NOT_MATCHED);
        }
        LocalDateTime signTime = LocalDateTime.now();
        return new TokensResponse(
                jwtTokenProvider.generateAccessToken(username, signTime),
                jwtTokenProvider.generateRefreshToken(username, signTime)
        );
    }
}
