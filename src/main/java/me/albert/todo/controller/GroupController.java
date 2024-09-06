package me.albert.todo.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.AssignTodoToGroupRequest;
import me.albert.todo.controller.dto.request.GroupRequest;
import me.albert.todo.controller.dto.request.UnassignTodoToGroupRequest;
import me.albert.todo.service.GroupService;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
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
public class GroupController {

    private final GroupService groupService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/groups")
    public IdResponse create(@Valid @RequestBody GroupRequest request, @CurrentUsername String username) {
        return groupService.create(request.name(), request.description(), username);
    }

    @PutMapping("/groups/{id}")
    public void update(
            @Valid @RequestBody GroupRequest request,
            @PathVariable Long id,
            @CurrentUsername String username
    ) {
        groupService.update(id, request.name(), request.description(), username);
    }

    @GetMapping("/groups")
    public List<GroupResponse> list(@CurrentUsername String username, @PageableDefault(size = 20) Pageable pageable) {
        return groupService.list(username, pageable);
    }

    @PutMapping("/groups/{id}/todos")
    public void assignTodo(
            @PathVariable Long id,
            @Valid @RequestBody AssignTodoToGroupRequest request,
            @CurrentUsername String username
    ) {
        groupService.assignTodos(id, request.todoIds(), username);
    }

    @DeleteMapping("/groups/{id}/todos")
    public void unassignTodo(
            @PathVariable Long id,
            @Valid @RequestBody UnassignTodoToGroupRequest request,
            @CurrentUsername String username
    ) {
        groupService.unassignTodos(id, request.todoIds(), username);
    }

    @GetMapping("/groups/{id}/todos")
    public List<TodoResponse> listTodos(@PathVariable Long id, @CurrentUsername String username) {
        return groupService.listTodos(id, username);
    }
}
