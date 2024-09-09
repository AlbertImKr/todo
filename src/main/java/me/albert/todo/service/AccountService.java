package me.albert.todo.service;

import java.util.List;
import me.albert.todo.domain.Account;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TokensResponse;

public interface AccountService {

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param username 유저 이름
     * @param password 비밀번호
     * @return
     * @throws me.albert.todo.exception.BusinessException 이미 존재하는 유저 이름일 경우 발생
     */
    IdResponse register(String username, String password);

    /**
     * 사용자를 로그인합니다.
     *
     * @param username 유저 이름
     * @param password 비밀번호
     * @return 토큰 정보
     * @throws me.albert.todo.exception.BusinessException 유저 이름이 존재하지 않거나 비밀번호가 일치하지 않을 경우 발생
     */
    TokensResponse login(String username, String password);

    /**
     * 사용자 이름으로 사용자를 조회합니다.
     *
     * @param username 유저 이름
     * @return 사용자 정보
     * @throws me.albert.todo.exception.BusinessException 유저 이름이 존재하지 않을 경우 발생
     */
    Account findByUsername(String username);

    /**
     * 사용자 ID로 사용자를 조회합니다.
     *
     * @param accountIds 사용자 ID
     * @return 사용자 정보
     * @throws me.albert.todo.exception.BusinessException 사용자 ID가 존재하지 않을 경우 발생
     */
    List<Account> findAllById(List<Long> accountIds);
}
