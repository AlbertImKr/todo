package me.albert.todo.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.albert.todo.domain.Account;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.repository.AccountRepository;
import me.albert.todo.service.dto.response.TokensResponse;
import me.albert.todo.utils.ErrorMessages;
import me.albert.todo.utils.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    public static final String USERNAME_OR_PASSWORD_NOT_MATCHED = "유저 이름 또는 비밀번호가 일치하지 않습니다.";
    public static final String USERNAME_NOT_EXISTED = "존재하지 않는 유저 이름입니다.";

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public void register(String username, String password) {
        if (accountRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorMessages.USERNAME_IS_EXISTED, HttpStatus.BAD_REQUEST);
        }
        accountRepository.save(new Account(username, password));
    }

    @Transactional(readOnly = true)
    @Override
    public TokensResponse login(String username, String password) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(USERNAME_OR_PASSWORD_NOT_MATCHED, HttpStatus.NOT_FOUND));
        if (!account.matchPassword(password)) {
            throw new BusinessException(USERNAME_OR_PASSWORD_NOT_MATCHED, HttpStatus.UNAUTHORIZED);
        }
        LocalDateTime signTime = LocalDateTime.now();
        return new TokensResponse(
                jwtTokenProvider.generateAccessToken(username, signTime),
                jwtTokenProvider.generateRefreshToken(username, signTime)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(USERNAME_NOT_EXISTED, HttpStatus.NOT_FOUND));
    }
}
