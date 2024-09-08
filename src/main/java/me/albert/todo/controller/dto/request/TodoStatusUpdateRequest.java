package me.albert.todo.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.utils.ValidationMessages;

public record TodoStatusUpdateRequest(
        @NotNull(message = ValidationMessages.TODO_STATUS_NOT_NULL)
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        TodoStatus status
) {

}
