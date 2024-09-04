package me.albert.todo.service;

public interface AccountService {

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param username        유저 이름
     * @param password        비밀번호
     * @throws IllegalArgumentException 이미 존재하는 유저 이름일 경우 발생
     */
    void register(String username, String password);
}
