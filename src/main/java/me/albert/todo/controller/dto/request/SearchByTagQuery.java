package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import me.albert.todo.utils.ValidationConstraints;
import me.albert.todo.utils.ValidationMessages;

public record SearchByTagQuery(
        @NotBlank(message = ValidationMessages.TAG_NAME_NOT_NULL)
        @Size(max = ValidationConstraints.TAG_NAME_MAX_LENGTH, message = ValidationMessages.TAG_NAME_MESSAGE)
        String tag
) {

}
