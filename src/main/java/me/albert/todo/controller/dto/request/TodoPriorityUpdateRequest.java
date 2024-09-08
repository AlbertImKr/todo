package me.albert.todo.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import me.albert.todo.domain.TodoPriority;
import me.albert.todo.utils.ValidationMessages;

public record TodoPriorityUpdateRequest(
        @NotNull(message = ValidationMessages.TODO_PRIORITY_MESSAGE)
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        TodoPriority priority
) {

}
