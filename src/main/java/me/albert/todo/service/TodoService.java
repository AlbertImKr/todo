package me.albert.todo.service;

import me.albert.todo.service.dto.request.TodoCreateRequest;
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
}
