package me.albert.todo.service;

import java.time.Duration;
import me.albert.todo.service.dto.response.IdResponse;

public interface RecurringTaskService {

    /**
     * 할 일에 대한 반복 작업을 생성합니다.
     *
     * @param todoId   할 일 ID
     * @param duration 반복 주기
     * @param username 사용자 이름
     * @return 생성된 반복 작업 ID
     */
    IdResponse createRecurringTask(Long todoId, Duration duration, String username);
}
