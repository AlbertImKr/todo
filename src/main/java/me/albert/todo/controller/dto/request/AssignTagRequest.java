package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.albert.todo.utils.ValidationMessages;

public record AssignTagRequest(
        @NotNull(message = ValidationMessages.TAG_ID_NOT_NULL)
        @Positive(message = ValidationMessages.TAG_ID_POSITIVE)
        Long tagId
) {

}
