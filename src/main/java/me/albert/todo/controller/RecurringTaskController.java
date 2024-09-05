package me.albert.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.albert.todo.controller.dto.request.RecurringTaskCreateRequest;
import me.albert.todo.controller.dto.request.RecurringTaskUpdateRequest;
import me.albert.todo.service.RecurringTaskService;
import me.albert.todo.service.dto.response.IdResponse;
import me.albert.todo.service.dto.response.RecurringTaskResponse;
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
public class RecurringTaskController {

    private final RecurringTaskService recurringTaskService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos/{todoId}/recurring-tasks")
    public IdResponse createRecurringTask(
            @PathVariable Long todoId,
            @Valid @RequestBody RecurringTaskCreateRequest request,
            @CurrentUsername String username
    ) {
        return recurringTaskService.createRecurringTask(todoId, request.recurrencePattern(), username);
    }

    @PutMapping("/todos/{todoId}/recurring-tasks/{recurringTaskId}")
    public void updateRecurringTask(
            @PathVariable Long todoId,
            @PathVariable Long recurringTaskId,
            @Valid @RequestBody RecurringTaskUpdateRequest request,
            @CurrentUsername String username
    ) {
        recurringTaskService.updateRecurringTask(recurringTaskId, request.recurrencePattern(), todoId, username);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/todos/{todoId}/recurring-tasks/{recurringTaskId}")
    public void deleteRecurringTask(
            @PathVariable Long todoId,
            @PathVariable Long recurringTaskId,
            @CurrentUsername String username
    ) {
        recurringTaskService.deleteRecurringTask(recurringTaskId, todoId, username);
    }

    @GetMapping("/todos/{todoId}/recurring-tasks/{recurringTaskId}")
    public RecurringTaskResponse getRecurringTask(
            @PathVariable Long todoId,
            @PathVariable Long recurringTaskId,
            @CurrentUsername String username
    ) {
        return recurringTaskService.getRecurringTask(recurringTaskId, todoId, username);
    }
}
