package me.albert.todo.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.utils.DateFormats;
import me.albert.todo.utils.ValidationConstraints;
import me.albert.todo.utils.ValidationMessages;

public record TodoUpdateRequest(
        @Size(min = ValidationConstraints.TODO_TITLE_MIN_LENGTH, max = ValidationConstraints.TODO_TITLE_MAX_LENGTH,
                message = ValidationMessages.TODO_TITLE_MESSAGE)
        String title,
        @NotBlank(message = ValidationMessages.TODO_DESCRIPTION_NOT_NULL)
        @Size(max = ValidationConstraints.TODO_DESCRIPTION_MAX_LENGTH, message = ValidationMessages.TODO_DESCRIPTION_MESSAGE)
        String description,
        @NotNull(message = ValidationMessages.TODO_DUE_DATE_NOT_NULL)
        @JsonFormat(pattern = DateFormats.DATE_TIME_FORMAT, timezone = DateFormats.TIME_ZONE)
        @Future(message = ValidationMessages.TODO_DUE_DATE_FUTURE)
        LocalDateTime dueDate,
        @NotNull(message = ValidationMessages.TODO_STATUS_NOT_NULL)
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        TodoStatus status
) {

}
