package me.albert.todo.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.AddUserToGroupRequest;
import me.albert.todo.controller.dto.request.AddUserToTodoRequest;
import me.albert.todo.controller.dto.request.AssignTagRequest;
import me.albert.todo.controller.dto.request.AssignTodoToGroupRequest;
import me.albert.todo.controller.dto.request.AssignTodoToProjectRequest;
import me.albert.todo.controller.dto.request.DeleteUserToGroupRequest;
import me.albert.todo.controller.dto.request.DeleteUserToTodoRequest;
import me.albert.todo.controller.dto.request.GroupRequest;
import me.albert.todo.controller.dto.request.ProjectCreateRequest;
import me.albert.todo.controller.dto.request.ProjectUpdateRequest;
import me.albert.todo.controller.dto.request.TodoPriorityUpdateRequest;
import me.albert.todo.controller.dto.request.TodoStatusUpdateRequest;
import me.albert.todo.controller.dto.request.UnAssignTodoToProjectRequest;
import me.albert.todo.controller.dto.request.UnassignTodoToGroupRequest;
import me.albert.todo.service.GroupService;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.AccountResponse;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.GroupTodoDetailResponse;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
import org.springframework.data.domain.Page;
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

    /**
     * 그룹을 생성 API
     *
     * @param request  그룹 생성 요청 DTO
     * @param username 현재 사용자 이름
     * @return 생성된 그룹 ID
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/groups")
    public IdResponse create(@Valid @RequestBody GroupRequest request, @CurrentUsername String username) {
        return groupService.create(request.name(), request.description(), username);
    }

    /**
     * 그룹을 삭제 API
     *
     * @param id       그룹 ID
     * @param username 현재 사용자 이름
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/groups/{id}")
    public void delete(@PathVariable Long id, @CurrentUsername String username) {
        groupService.delete(id, username);
    }

    /**
     * 그룹을 수정 API
     *
     * @param request  그룹 수정 요청 DTO
     * @param id       그룹 ID
     * @param username 현재 사용자 이름
     */
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

    /**
     * 그룹에 할 일 할당 API
     *
     * @param id       그룹 ID
     * @param request  할 일 할당 요청 DTO
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/todos")
    public void assignTodo(
            @PathVariable Long id,
            @Valid @RequestBody AssignTodoToGroupRequest request,
            @CurrentUsername String username
    ) {
        groupService.assignTodos(id, request.todoIds(), username);
    }

    /**
     * 그룹에서 할 일 제거 API
     *
     * @param id       그룹 ID
     * @param request  할 일 할당 요청 DTO
     * @param username 현재 사용자 이름
     */
    @DeleteMapping("/groups/{id}/todos")
    public void unassignTodo(
            @PathVariable Long id,
            @Valid @RequestBody UnassignTodoToGroupRequest request,
            @CurrentUsername String username
    ) {
        groupService.unassignTodos(id, request.todoIds(), username);
    }

    /**
     * 할 일을 사용자에게 할당 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param request  사용자 할당 요청 DTO
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/todos/{todoId}/users")
    public void assignTodoToUser(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @Valid @RequestBody AddUserToTodoRequest request,
            @CurrentUsername String username
    ) {
        groupService.assignTodoToUsers(id, todoId, request.accountIds(), username);
    }

    /**
     * 할 일을 사용자에서 제거 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param request  사용자 할당 해제 요청 DTO
     * @param username 현재 사용자 이름
     */
    @DeleteMapping("/groups/{id}/todos/{todoId}/users")
    public void unassignTodoFromUser(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @Valid @RequestBody DeleteUserToTodoRequest request,
            @CurrentUsername String username
    ) {
        groupService.unassignTodoFromUsers(id, todoId, request.accountIds(), username);
    }

    /**
     * 그룹 사용자 추가 API
     *
     * @param id       그룹 ID
     * @param request  사용자 할당 요청 DTO
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/users")
    public void addAccounts(
            @PathVariable Long id,
            @Valid @RequestBody AddUserToGroupRequest request,
            @CurrentUsername String username
    ) {
        groupService.addAccounts(id, request.accountIds(), username);
    }

    /**
     * 그룹 사용자 제거 API
     *
     * @param id       그룹 ID
     * @param request  사용자 할당 요청 DTO
     * @param username 현재 사용자 이름
     */
    @DeleteMapping("/groups/{id}/users")
    public void removeAccounts(
            @PathVariable Long id,
            @Valid @RequestBody DeleteUserToGroupRequest request,
            @CurrentUsername String username
    ) {
        groupService.removeAccounts(id, request.accountIds(), username);
    }

    /**
     * 그룹 사용자 목록 조회 API
     *
     * @param id       그룹 ID
     * @param username 현재 사용자 이름
     * @return 그룹에 속한 사용자 목록
     */
    @GetMapping("/groups/{id}/users")
    public List<AccountResponse> listAccounts(@PathVariable Long id, @CurrentUsername String username) {
        return groupService.listAccounts(id, username);
    }

    @GetMapping("/groups/{id}/todos")
    public List<TodoResponse> listTodos(@PathVariable Long id, @CurrentUsername String username) {
        return groupService.listTodos(id, username);
    }

    /**
     * 그룹 할 일 수정 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/todos/{todoId}")
    public void editTodo(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @RequestBody TodoUpdateRequest request,
            @CurrentUsername String username
    ) {
        groupService.editTodo(id, todoId, request, username);
    }

    /**
     * 그룹 할 일 상태 수정 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param request  할 일 상태 수정 요청 DTO
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/todos/{todoId}/status")
    public void updateTodoStatus(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @RequestBody TodoStatusUpdateRequest request,
            @CurrentUsername String username
    ) {
        groupService.updateTodoStatus(id, todoId, request.status(), username);
    }

    /**
     * 그룹 할 일 우선순위 수정 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param request  할 일 우선순위 수정 요청 DTO
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/todos/{todoId}/priority")
    public void updateTodoPriority(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @RequestBody TodoPriorityUpdateRequest request,
            @CurrentUsername String username
    ) {
        groupService.updateTodoPriority(id, todoId, request.priority(), username);
    }

    /**
     * 그룹 할 일에 태그를 할당하는 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param request  태그 할당 요청
     * @param username 현재 사용자 이름
     */
    @PutMapping("/groups/{id}/todos/{todoId}/tags")
    public void assignTag(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @Valid @RequestBody AssignTagRequest request,
            @CurrentUsername String username
    ) {
        groupService.assignTag(id, todoId, request.tagId(), username);
    }

    /**
     * 그룹 할 일에 할당된 태그를 해제하는 API
     *
     * @param id       그룹 ID
     * @param todoId   할 일 ID
     * @param tagId    태그 ID
     * @param username 현재 사용자 이름
     */
    @DeleteMapping("/groups/{id}/todos/{todoId}/tags/{tagId}")
    public void unassignTag(
            @PathVariable Long id,
            @PathVariable Long todoId,
            @PathVariable Long tagId,
            @CurrentUsername String username
    ) {
        groupService.unassignTag(id, todoId, tagId, username);
    }

    /**
     * 그룹에 프로젝트 생성 API
     *
     * @param id       그룹 ID
     * @param request  프로젝트 생성 요청 DTO
     * @param username 현재 사용자 이름
     * @return 생성된 프로젝트 ID
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/groups/{id}/projects")
    public IdResponse createProject(
            @PathVariable Long id,
            @RequestBody ProjectCreateRequest request,
            @CurrentUsername String username
    ) {
        return groupService.createProject(id, request.name(), username);
    }

    /**
     * 그룹에 프로젝트 수정 API
     *
     * @param id        그룹 ID
     * @param projectId 프로젝트 ID
     * @param request   프로젝트 수정 요청 DTO
     * @param username  현재 사용자 이름
     */
    @PutMapping("/groups/{id}/projects/{projectId}")
    public void updateProject(
            @PathVariable Long id,
            @PathVariable Long projectId,
            @RequestBody ProjectUpdateRequest request,
            @CurrentUsername String username
    ) {
        groupService.updateProject(id, projectId, request.name(), username);
    }

    /**
     * 그룹에 프로젝트 삭제 API
     *
     * @param id        그룹 ID
     * @param projectId 프로젝트 ID
     * @param username  현재 사용자 이름
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/groups/{id}/projects/{projectId}")
    public void deleteProject(@PathVariable Long id, @PathVariable Long projectId, @CurrentUsername String username) {
        groupService.deleteProject(id, projectId, username);
    }

    /**
     * 그룹에 할 일을 프로젝트에 할당하는 API
     *
     * @param id        그룹 ID
     * @param projectId 프로젝트 ID
     * @param request   할 일 할당 요청 DTO
     * @param username  현재 사용자 이름
     */
    @PutMapping("/groups/{id}/projects/{projectId}/todos")
    public void assignTodoToProject(
            @PathVariable Long id,
            @PathVariable Long projectId,
            @Valid @RequestBody AssignTodoToProjectRequest request,
            @CurrentUsername String username
    ) {
        groupService.assignTodosToProject(id, projectId, request.todoIds(), username);
    }

    /**
     * 그룹에 할 일을 프로젝트에서 제거하는 API
     *
     * @param id        그룹 ID
     * @param projectId 프로젝트 ID
     * @param request   할 일 제거 요청 DTO
     * @param username  현재 사용자 이름
     */
    @DeleteMapping("/groups/{id}/projects/{projectId}/todos")
    public void unassignTodoFromProject(
            @PathVariable Long id,
            @PathVariable Long projectId,
            @Valid @RequestBody UnAssignTodoToProjectRequest request,
            @CurrentUsername String username
    ) {
        groupService.unassignTodosFromProject(id, projectId, request.todoIds(), username);
    }

    /**
     * 그룹에 프로젝트 할 일 목록 조회 API
     *
     * @param id        그룹 ID
     * @param projectId 프로젝트 ID
     * @param username  현재 사용자 이름
     * @return 할 일 목록
     */
    @GetMapping("/groups/{id}/projects/{projectId}/todos")
    public Page<GroupTodoDetailResponse> listProjectTodos(
            @PathVariable Long id, @PathVariable Long projectId, @CurrentUsername String username,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return groupService.listProjectTodos(id, projectId, username, pageable);
    }
}
