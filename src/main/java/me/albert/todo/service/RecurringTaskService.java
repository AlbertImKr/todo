package me.albert.todo.service;

import java.time.Period;

public interface RecurringTaskService {

    /**
     * 할 일에 대한 반복 작업을 업데이트합니다.
     *
     * @param todoId            할 일 ID
     * @param recurrencePattern 반복 주기
     * @param username          사용자 이름
     */
    void updateRecurringTask(Long todoId, Period recurrencePattern, String username);

    /**
     * 할 일에 대한 반복 작업을 삭제합니다.
     *
     * @param todoId   할 일 ID
     * @param username 사용자 이름
     * @throws me.albert.todo.exception.BusinessException 반복 작업이 존재하지 않을 경우
     */
    void deleteRecurringTask(Long todoId, String username);
}
