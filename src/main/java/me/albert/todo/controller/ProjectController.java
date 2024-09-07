package me.albert.todo.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.AssignTodoToProjectRequest;
import me.albert.todo.controller.dto.request.ProjectCreateRequest;
import me.albert.todo.controller.dto.request.ProjectUpdateRequest;
import me.albert.todo.service.ProjectService;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.ProjectResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 생성 API
     *
     * @param request  프로젝트 생성 요청
     * @param username 현재 사용자의 이름
     * @return 생성된 프로젝트의 ID
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/projects")
    public IdResponse createProject(
            @Valid @RequestBody ProjectCreateRequest request, @CurrentUsername String username
    ) {
        return projectService.createProject(request.name(), username);
    }

    /**
     * 프로젝트 수정 API
     *
     * @param projectId 프로젝트 ID
     * @param request   프로젝트 수정 요청
     * @param username  현재 사용자의 이름
     */
    @PutMapping("/projects/{projectId}")
    public void updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectUpdateRequest request,
            @CurrentUsername String username
    ) {
        projectService.updateProject(projectId, request.name(), username);
    }

    /**
     * 프로젝트 삭제 API
     *
     * @param projectId 프로젝트 ID
     * @param username  현재 사용자의 이름
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(
            @PathVariable Long projectId,
            @CurrentUsername String username
    ) {
        projectService.deleteProject(projectId, username);
    }

    /**
     * 프로젝트 목록 조회 API
     *
     * @param username 현재 사용자의 이름
     * @return 프로젝트 목록
     */
    @GetMapping("/projects")
    public List<ProjectResponse> getProjects(@CurrentUsername String username, @PageableDefault Pageable pageable) {
        return projectService.getProjects(username, pageable);
    }

    /**
     * 프로젝트에 할 일 할당 API
     *
     * @param projectId 프로젝트 ID
     * @param request   할 일 할당 요청
     * @param username  현재 사용자의 이름
     */
    @PutMapping("/projects/{projectId}/todos")
    public void assignTodoToProject(
            @PathVariable Long projectId,
            @Valid @RequestBody AssignTodoToProjectRequest request,
            @CurrentUsername String username
    ) {
        projectService.assignTodoToProject(projectId, request.todoIds(), username);
    }
}
