package me.albert.todo.service;

import java.util.List;
import me.albert.todo.service.dto.response.GroupResponse;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.TodoResponse;
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
     * @param groupId          그룹 ID
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
     * @param id       그룹 ID
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 그룹이 존재하지 않거나 그룹의 소유자가 아닐 경우 발생
     */
    void delete(Long id, String username);
}
