package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.ProjectCreateRequest;
import me.albert.todo.controller.dto.request.ProjectUpdateRequest;
import me.albert.todo.service.ProjectService;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
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
}
