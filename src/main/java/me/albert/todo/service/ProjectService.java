package me.albert.todo.service;

import me.albert.todo.service.dto.response.IdResponse;

public interface ProjectService {

    /**
     * 프로젝트를 생성합니다.
     *
     * @param name     프로젝트 이름
     * @param username 사용자 이름
     * @return 생성된 프로젝트의 ID
     */
    IdResponse createProject(String name, String username);
}
