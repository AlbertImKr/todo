package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.AssignUserRequest;
import me.albert.todo.controller.dto.request.UnassignUserRequest;
import me.albert.todo.service.TodoService;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoStatusUpdateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
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
public class TodoController {

    private final TodoService todoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos")
    public IdResponse create(@Valid @RequestBody TodoCreateRequest request, @CurrentUsername String username) {
        return todoService.create(request, username);
    }

    @PutMapping("/todos/{id}")
    public void update(
            @Valid @RequestBody TodoUpdateRequest request, @PathVariable Long id, @CurrentUsername String username
    ) {
        todoService.update(request, id, username);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/todos/{id}")
    public void delete(@PathVariable Long id, @CurrentUsername String username) {
        todoService.delete(id, username);
    }

    @GetMapping("/todos/{id}")
    public TodoResponse get(@PathVariable Long id, @CurrentUsername String username) {
        return todoService.get(id, username);
    }

    @PutMapping("/todos/{id}/status")
    public void updateStatus(
            @PathVariable Long id, @Valid @RequestBody TodoStatusUpdateRequest request, @CurrentUsername String username
    ) {
        todoService.updateStatus(id, request.status(), username);
    }

    @PutMapping("/todos/{id}/users")
    public void assignUser(
            @PathVariable Long id,
            @Valid @RequestBody AssignUserRequest request,
            @CurrentUsername String currentUsername
    ) {
        todoService.assignUser(id, request.username(), currentUsername);
    }

    @DeleteMapping("/todos/{id}/users")
    public void unassignUser(
            @PathVariable Long id,
            @Valid @RequestBody UnassignUserRequest request,
            @CurrentUsername String currentUsername
    ) {
        todoService.unassignUser(id, request.username(), currentUsername);
    }
}
