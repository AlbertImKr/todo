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

    /**
     * 할 일을 생성 API 입니다. 할일의 상태는 PENDING으로 설정됩니다.
     *
     * @param request  생성할 할 일 정보
     * @param username 사용자 이름
     * @return 생성된 할 일의 ID
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos")
    public IdResponse create(@Valid @RequestBody TodoCreateRequest request, @CurrentUsername String username) {
        return todoService.create(request, username);
    }

    /**
     * 할 일을 수정 API 입니다.
     *
     * @param request  수정할 할 일 정보
     * @param id       수정할 할 일 ID
     * @param username 사용자 이름
     */
    @PutMapping("/todos/{id}")
    public void update(
            @Valid @RequestBody TodoUpdateRequest request, @PathVariable Long id, @CurrentUsername String username
    ) {
        todoService.update(request, id, username);
    }

    /**
     * 할 일을 삭제 API 입니다. 응답 코드는 204(No Content) 입니다.
     *
     * @param id       삭제할 할 일 ID
     * @param username 사용자 이름
     */
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
