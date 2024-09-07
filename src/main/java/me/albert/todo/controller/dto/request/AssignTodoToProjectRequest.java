package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import me.albert.todo.utils.ValidationMessages;

public record AssignTodoToProjectRequest(
        @NotEmpty(message = ValidationMessages.PROJECT_ASSIGN_TODO_NOT_NULL)
        List<Long> todoIds
) {

}
