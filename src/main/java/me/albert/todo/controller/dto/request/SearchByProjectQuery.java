package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.albert.todo.utils.ValidationMessages;

public record SearchByProjectQuery(
        @NotNull(message = ValidationMessages.PROJECT_ID_NOT_NULL)
        @Positive(message = ValidationMessages.PROJECT_ID_POSITIVE)
        Long projectId
) {

}
