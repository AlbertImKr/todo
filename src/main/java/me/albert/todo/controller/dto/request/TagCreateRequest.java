package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import me.albert.todo.utils.ValidationMessages;

public record TagCreateRequest(
        @NotBlank(message = ValidationMessages.TAG_NAME_NOT_NULL)
        @Size(max = 20, message = ValidationMessages.TAG_NAME_MESSAGE)
        String name
) {

}
