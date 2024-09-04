package me.albert.todo.service;

import me.albert.todo.service.dto.response.TokensResponse;

public interface AccountService {

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param username 유저 이름
     * @param password 비밀번호
     * @throws IllegalArgumentException 이미 존재하는 유저 이름일 경우 발생
     */
    void register(String username, String password);

    /**
     * 사용자를 로그인합니다.
     *
     * @param username 유저 이름
     * @param password 비밀번호
     * @return 토큰 정보
     * @throws IllegalArgumentException 유저 이름이 존재하지 않거나 비밀번호가 일치하지 않을 경우 발생
     */
    TokensResponse login(String username, String password);
}
