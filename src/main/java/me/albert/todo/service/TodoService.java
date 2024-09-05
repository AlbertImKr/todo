package me.albert.todo.service;

import me.albert.todo.service.dto.request.TodoCreateRequest;
import me.albert.todo.service.dto.request.TodoUpdateRequest;
import me.albert.todo.service.dto.response.IdResponse;

public interface TodoService {

    /**
     * 할 일을 생성합니다.
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
}
