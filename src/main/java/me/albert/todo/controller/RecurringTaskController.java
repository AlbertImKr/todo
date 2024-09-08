package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.RecurringTaskUpdateRequest;
import me.albert.todo.service.RecurringTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RecurringTaskController {

    private final RecurringTaskService recurringTaskService;

    /**
     * 할 일에 반복 작업을 업데이트하는 API
     *
     * @param todoId   할 일 ID
     * @param request  반복 작업 생성 요청,ISO-8601 기간 형식
     * @param username 사용자 이름
     */
    @PutMapping("/todos/{todoId}/recurring-tasks")
    public void updateRecurringTask(
            @PathVariable Long todoId,
            @Valid @RequestBody RecurringTaskUpdateRequest request,
            @CurrentUsername String username
    ) {
        recurringTaskService.updateRecurringTask(todoId, request.recurrencePattern(), username);
    }

    /**
     * 할 일에 반복 작업을 수정하는 API
     *
     * @param todoId   할 일 ID
     * @param username 사용자 이름
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/todos/{todoId}/recurring-tasks/")
    public void deleteRecurringTask(
            @PathVariable Long todoId,
            @CurrentUsername String username
    ) {
        recurringTaskService.deleteRecurringTask(todoId, username);
    }
}
