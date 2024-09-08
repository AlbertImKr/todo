package me.albert.todo.service;

import lombok.RequiredArgsConstructor;
import me.albert.todo.service.dto.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TodoFacade {

    private final ProjectService projectService;
    private final TodoService todoService;

    @Transactional(readOnly = true)
    public Page<TodoResponse> listByProject(Long projectId, String username, Pageable pageable) {
        projectService.validateProjectId(projectId, username);
        return todoService.listByProject(projectId, username, pageable);
    }
}
