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

    /**
     * 프로젝트를 수정합니다.
     *
     * @param projectId 프로젝트 ID
     * @param name      프로젝트 이름
     * @param username  사용자 이름
     * @throws me.albert.todo.exception.BusinessException 프로젝트를 찾을 수 없는 경우
     */
    void updateProject(Long projectId, String name, String username);
}
