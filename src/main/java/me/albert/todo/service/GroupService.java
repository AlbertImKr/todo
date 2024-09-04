package me.albert.todo.service;

import me.albert.todo.service.dto.response.IdResponse;

public interface GroupService {
    /**
     * 그룹을 생성합니다.
     *
     * @param name        그룹 이름
     * @param description 그룹 설명
     * @param username    사용자 이름
     * @return 생성된 그룹의 ID
     * @throws IllegalArgumentException 사용자 이름이 존재하지 않을 경우 발생
     */
    IdResponse create(String name, String description, String username);
}
