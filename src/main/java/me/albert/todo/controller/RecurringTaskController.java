package me.albert.todo.controller;

import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.RecurringTaskCreateRequest;
import me.albert.todo.service.RecurringTaskService;
import me.albert.todo.service.dto.response.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RecurringTaskController {

    private final RecurringTaskService recurringTaskService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos/{todoId}/recurring-tasks")
    public IdResponse createRecurringTask(
            @PathVariable Long todoId, @RequestBody RecurringTaskCreateRequest request, @CurrentUsername String username
    ) {
        return recurringTaskService.createRecurringTask(todoId, request.recurrencePattern(), username);
    }
}
