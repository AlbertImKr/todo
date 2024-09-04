package me.albert.todo.controller;

import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.UserLoginRequest;
import me.albert.todo.controller.dto.request.UserRegisterRequest;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.service.AccountService;
import me.albert.todo.service.dto.response.TokensResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    public static final String PASSWORD_NOT_MATCHED = "비밀번호가 일치하지 않습니다.";

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public void register(@Valid @RequestBody UserRegisterRequest request) {
        if (!Objects.equals(request.password(), request.confirmPassword())) {
            throw new BusinessException(PASSWORD_NOT_MATCHED, HttpStatus.BAD_REQUEST);
        }
        accountService.register(request.username(), request.password());
    }

    @PostMapping("/users/login")
    public TokensResponse login(@Valid @RequestBody UserLoginRequest request) {
        return accountService.login(request.username(), request.password());
    }
}
