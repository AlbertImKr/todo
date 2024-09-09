package me.albert.todo.controller;

import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.UserLoginRequest;
import me.albert.todo.controller.dto.request.UserRegisterRequest;
import me.albert.todo.exception.BusinessException;
import me.albert.todo.service.AccountService;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TokensResponse;
import me.albert.todo.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    /**
     * 회원가입
     *
     * @param request 회원가입 요청
     * @return 회원 ID
     * @throws BusinessException 비번이 일치하지 않을 경우
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public IdResponse register(@Valid @RequestBody UserRegisterRequest request) {
        if (!Objects.equals(request.password(), request.confirmPassword())) {
            throw new BusinessException(ErrorMessages.PASSWORD_NOT_MATCHED, HttpStatus.BAD_REQUEST);
        }
        return accountService.register(request.username(), request.password());
    }

    /**
     * 로그인
     *
     * @param request 로그인 요청
     * @return 토큰
     */
    @PostMapping("/users/login")
    public TokensResponse login(@Valid @RequestBody UserLoginRequest request) {
        return accountService.login(request.username(), request.password());
    }
}
