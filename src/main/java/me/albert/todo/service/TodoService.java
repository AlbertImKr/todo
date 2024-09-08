package me.albert.todo.service;

import java.time.Duration;
import java.util.List;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoPriority;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    /**
     * 할 일을 생성합니다. 할일의 상태는 PENDING으로 설정됩니다.
     *
     * @param request  생성할 할 일 정보
     * @param username 사용자 이름
     * @return 생성된 할 일의 ID
     */
    IdResponse create(TodoCreateRequest request, String username);

    /**
     * 할 일을 수정합니다.
     *
     * @param request  수정할 할 일 정보
     * @param id       할 일 ID
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    void update(TodoUpdateRequest request, Long id, String username);

    /**
     * 할 일을 삭제합니다.
     *
     * @param id       할 일 ID
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    void delete(Long id, String username);

    /**
     * 할 일을 조회합니다.
     *
     * @param id       할 일 ID
     * @param username 사용자 이름
     * @return 할 일 정보
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    TodoResponse get(Long id, String username);

    /**
     * 할 일의 상태를 수정합니다.
     *
     * @param id       할 일 ID
     * @param status   변경할 상태
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    void updateStatus(Long id, TodoStatus status, String username);

    /**
     * 할 일을 ID로 조회합니다.
     *
     * @param todoId   할 일 ID
     * @param username 사용자 이름
     * @return 할 일 정보
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    Todo getTodoByIdAndUsername(Long todoId, String username);

    /**
     * 할 일에 사용자를 할당합니다.
     *
     * @param todoId          할 일 ID
     * @param username        사용자 이름
     * @param currentUsername 현재 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    void assignUser(Long todoId, String username, String currentUsername);

    /**
     * 할 일에 사용자를 해제합니다.
     *
     * @param todoId          할 일 ID
     * @param username        사용자 이름
     * @param currentUsername 현재 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     */
    void unassignUser(Long todoId, String username, String currentUsername);

    /**
     * ID 목록으로 할 일을 조회합니다.
     *
     * @param todoIds  할 일 ID 목록
     * @param username 사용자 이름
     * @return 할 일 목록
     */
    List<Todo> findAllByIdInAndOwner(List<Long> todoIds, String username);

    /**
     * 할 일에 태그를 할당합니다.
     *
     * @param todoId          할 일 ID
     * @param tagId           태그 ID
     * @param currentUsername 현재 사용자 이름
     */
    void assignTag(Long todoId, Long tagId, String currentUsername);

    /**
     * 할 일에 할당된 태그를 해제합니다.
     *
     * @param todoId          할 일 ID
     * @param tagId           태그 ID
     * @param currentUsername 현재 사용자 이름
     */
    void unassignTag(Long todoId, Long tagId, String currentUsername);

    /**
     * 할 일의 우선순위를 업데이트합니다.
     *
     * @param id              할 일 ID
     * @param priority        변경할 우선순위
     * @param currentUsername 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     * @throws me.albert.todo.exception.BusinessException 할 일의 소유자가 아닌 경우
     */
    void updatePriority(Long id, TodoPriority priority, String currentUsername);

    /**
     * 알림 설정을 업데이트합니다.
     *
     * @param id              할 일 ID
     * @param durations       변경할 알림 설정
     * @param currentUsername 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     * @throws me.albert.todo.exception.BusinessException 할 일의 소유자가 아닌 경우
     */
    void updateNotificationSettings(Long id, List<Duration> durations, String currentUsername);

    /**
     * 알림 설정을 삭제합니다.
     *
     * @param id              할 일 ID
     * @param currentUsername 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 할 일을 찾을 수 없는 경우
     * @throws me.albert.todo.exception.BusinessException 할 일의 소유자가 아닌 경우
     */
    void deleteNotificationSettings(Long id, String currentUsername);

    /**
     * 사용자의 할 일 목록을 조회합니다.
     *
     * @param username 사용자 이름
     * @param pageable 페이징 정보
     * @return 할 일 목록
     */
    Page<TodoResponse> list(String username, Pageable pageable);
}
