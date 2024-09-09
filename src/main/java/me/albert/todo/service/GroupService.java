package me.albert.todo.service;

import java.time.Period;
import java.util.List;
import me.albert.todo.domain.TodoPriority;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.AccountResponse;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.GroupTodoDetailResponse;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    /**
     * 그룹을 생성합니다.
     *
     * @param name        그룹 이름
     * @param description 그룹 설명
     * @param username    사용자 이름
     * @return 생성된 그룹의 ID
     * @throws me.albert.todo.exception.BusinessException 사용자 이름이 존재하지 않을 경우 발생
     */
    IdResponse create(String name, String description, String username);

    /**
     * 그룹을 수정합니다.
     *
     * @param groupId     그룹 ID
     * @param name        그룹 이름
     * @param description 그룹 설명
     * @param username    사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     * @throws me.albert.todo.exception.BusinessException 사용자 이름이 존재하지 않을 경우 발생
     */
    void update(long groupId, String name, String description, String username);

    /**
     * 사용자의 그룹 목록을 조회합니다.
     *
     * @param username 사용자 이름
     * @param pageable 페이지 정보
     */
    List<GroupResponse> list(String username, Pageable pageable);

    /**
     * 그룹에 할일을 할당합니다.
     *
     * @param groupId  그룹 ID
     * @param todoIds  할일 ID 목록
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     */
    void assignTodos(Long groupId, List<Long> todoIds, String username);

    /**
     * 그룹에서 할일을 해제합니다.
     *
     * @param groupId  그룹 ID
     * @param todoIds  할일 ID 목록
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     */
    void unassignTodos(Long groupId, List<Long> todoIds, String username);

    /**
     * 그룹의 할일 목록을 조회합니다.
     *
     * @param groupId  그룹 ID
     * @param username 사용자 이름
     * @return 그룹의 할일 목록
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     */
    List<TodoResponse> listTodos(Long groupId, String username);

    /**
     * 그룹을 삭제합니다.
     *
     * @param groupId  그룹 ID
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     */
    void delete(Long groupId, String username);

    /**
     * 그룹에 사용자를 추가합니다.
     *
     * @param groupId    그룹 ID
     * @param accountIds 사용자 ID 목록
     * @param username   사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     */
    void addAccounts(Long groupId, List<Long> accountIds, String username);

    /**
     * 그룹에서 사용자를 제거합니다.
     *
     * @param groupId  그룹 ID
     * @param longs    사용자 ID 목록
     * @param username 사용자 이름
     */
    void removeAccounts(Long groupId, List<Long> longs, String username);

    /**
     * 그룹에 속한 사용자 목록을 조회합니다.
     *
     * @param groupId  그룹 ID
     * @param username 사용자 이름
     * @return 그룹에 속한 사용자 목록
     */
    List<AccountResponse> listAccounts(Long groupId, String username);

    /**
     * 할일을 사용자에게 할당합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param longs    사용자 ID 목록
     * @param username 사용자 이름
     */
    void assignTodoToUsers(Long groupId, Long todoId, List<Long> longs, String username);

    /**
     * 할일을 사용자에게서 해제합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param longs    사용자 ID 목록
     * @param username 사용자 이름
     */
    void unassignTodoFromUsers(Long groupId, Long todoId, List<Long> longs, String username);

    /**
     * 그룹에 속한 할 일을 수정합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param request  할 일 수정 요청
     * @param username 사용자 이름
     */
    void editTodo(Long groupId, Long todoId, TodoUpdateRequest request, String username);

    /**
     * 그룹에 속한 할 일의 상태를 수정합니다.
     *
     * @param id         그룹 ID
     * @param todoId     할 일 ID
     * @param todoStatus 할 일 상태
     * @param username   사용자 이름
     */
    void updateTodoStatus(Long id, Long todoId, TodoStatus todoStatus, String username);

    /**
     * 그룹에 속한 할 일의 우선순위를 수정합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param priority 할 일 우선순위
     * @param username 사용자 이름
     */
    void updateTodoPriority(Long groupId, Long todoId, TodoPriority priority, String username);

    /**
     * 할 일에 태그를 할당합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param tagId    태그 ID
     * @param username 사용자 이름
     */
    void assignTag(Long groupId, Long todoId, Long tagId, String username);

    /**
     * 할 일에 할당된 태그를 해제합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param tagId    태그 ID
     * @param username 사용자 이름
     */
    void unassignTag(Long groupId, Long todoId, Long tagId, String username);

    /**
     * 그룹에 프로젝트를 생성합니다.
     *
     * @param groupId  그룹 ID
     * @param name     프로젝트 이름
     * @param username 사용자 이름
     * @return 생성된 프로젝트의 ID
     */
    IdResponse createProject(Long groupId, String name, String username);

    /**
     * 그룹의 포로젝트를 업데이트합니다.
     *
     * @param groupId   그룹 ID
     * @param projectId 프로젝트 ID
     * @param name      프로젝트 이름
     * @param username  사용자 이름
     */
    void updateProject(Long groupId, Long projectId, String name, String username);

    /**
     * 그룹의 프로젝트를 삭제합니다.
     *
     * @param groupId   그룹 ID
     * @param projectId 프로젝트 ID
     * @param username  사용자 이름
     */
    void deleteProject(Long groupId, Long projectId, String username);

    /**
     * 그룹 프로젝트에 할 일을 할당합니다.
     *
     * @param groupId   그룹 ID
     * @param projectId 프로젝트 ID
     * @param todoIds   할 일 ID 목록
     * @param username  사용자 이름
     */
    void assignTodosToProject(Long groupId, Long projectId, List<Long> todoIds, String username);

    /**
     * 그룹 프로젝트에서 할 일을 해제합니다.
     *
     * @param groupId   그룹 ID
     * @param projectId 프로젝트 ID
     * @param todoIds   할 일 ID 목록
     * @param username  사용자 이름
     */
    void unassignTodosFromProject(Long groupId, Long projectId, List<Long> todoIds, String username);

    /**
     * 그룹 프로젝트의 할 일 목록을 조회합니다.
     *
     * @param id        그룹 ID
     * @param projectId 프로젝트 ID
     * @param username  사용자 이름
     * @param pageable  페이지 정보
     * @return 그룹 프로젝트의 할 일 목록
     */
    Page<GroupTodoDetailResponse> listProjectTodos(Long id, Long projectId, String username, Pageable pageable);

    /**
     * 그룹 할일의 반복 작업을 업데이트합니다.
     *
     * @param groupId  그룹 ID
     * @param todoId   할 일 ID
     * @param period   반복 주기
     * @param username 사용자 이름
     */
    void updateRecurringTask(Long groupId, Long todoId, Period period, String username);
}
