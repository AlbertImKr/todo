package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.Period;
import me.albert.todo.utils.ValidationMessages;

public record RecurringTaskUpdateRequest(
        @NotNull(message = ValidationMessages.RECURRING_TASK_RECURRENCE_PATTERN_MESSAGE)
        Period recurrencePattern
) {

}
