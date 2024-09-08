package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import me.albert.todo.utils.ValidationConstraints;
import me.albert.todo.utils.ValidationMessages;

public record ProjectCreateRequest(
        @NotBlank(message = ValidationMessages.PROJECT_NAME_NOT_NULL)
        @Size(max = ValidationConstraints.PROJECT_NAME_MAX_LENGTH, message = ValidationMessages.PROJECT_NAME_MESSAGE)
        String name
) {

}
