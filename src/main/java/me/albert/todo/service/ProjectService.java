package me.albert.todo.service;

import java.util.List;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.ProjectResponse;
import org.springframework.data.domain.Pageable;

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

    /**
     * 프로젝트를 삭제합니다.
     *
     * @param projectId 프로젝트 ID
     * @param username  사용자 이름
     * @throws me.albert.todo.exception.BusinessException 프로젝트를 찾을 수 없는 경우
     * @throws me.albert.todo.exception.BusinessException 프로젝트를 삭제할 수 없는 경우
     */
    void deleteProject(Long projectId, String username);

    /**
     * 사용자의 개인 프로젝트 목록을 조회합니다.
     *
     * @param username 사용자 이름
     * @param pageable 페이지 정보
     * @return 프로젝트 목록
     */
    List<ProjectResponse> getProjects(String username, Pageable pageable);
}
