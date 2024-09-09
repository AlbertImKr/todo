package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import me.albert.todo.utils.ValidationConstraints;
import me.albert.todo.utils.ValidationMessages;

public record GroupRequest(
        @NotBlank(message = ValidationMessages.GROUP_NAME_NOT_NULL)
        @Size(min = ValidationConstraints.GROUP_NAME_MIN_LENGTH, max = ValidationConstraints.GROUP_NAME_MAX_LENGTH,
                message = ValidationMessages.GROUP_NAME_MESSAGE)
        String name,
        @NotBlank(message = ValidationMessages.GROUP_DESCRIPTION_NOT_NULL)
        @Size(min = ValidationConstraints.GROUP_DESCRIPTION_MIN_LENGTH,
                max = ValidationConstraints.GROUP_DESCRIPTION_MAX_LENGTH,
                message = ValidationMessages.GROUP_DESCRIPTION_MESSAGE)
        String description
) {

}
