package me.albert.todo.service.dto.request;

import static me.albert.todo.utils.ValidationMessages.TODO_DESCRIPTION_NOT_NULL;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import me.albert.todo.utils.DateFormats;
import me.albert.todo.utils.ValidationConstraints;
import me.albert.todo.utils.ValidationMessages;

public record TodoCreateRequest(
        @NotNull(message = ValidationMessages.TODO_TITLE_NOT_NULL)
        @Size(min = ValidationConstraints.TODO_TITLE_MIN_LENGTH, max = ValidationConstraints.TODO_TITLE_MAX_LENGTH,
                message = ValidationMessages.TODO_TITLE_MESSAGE)
        String title,
        @NotBlank(message = TODO_DESCRIPTION_NOT_NULL)
        @Size(max = ValidationConstraints.TODO_DESCRIPTION_MAX_LENGTH, message = ValidationMessages.TODO_DESCRIPTION_MESSAGE)
        String description,
        @NotNull(message = ValidationMessages.TODO_DUE_DATE_NOT_NULL)
        @Future(message = ValidationMessages.TODO_DUE_DATE_FUTURE)
        @JsonFormat(pattern = DateFormats.DATE_TIME_FORMAT, timezone = DateFormats.TIME_ZONE)
        LocalDateTime dueDate
) {

}
