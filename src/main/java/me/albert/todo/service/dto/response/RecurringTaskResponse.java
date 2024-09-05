package me.albert.todo.service.dto.response;

import java.time.Period;
import me.albert.todo.domain.RecurringTask;

public record RecurringTaskResponse(
        Long id,
        Period recurrencePattern
) {

    public static RecurringTaskResponse from(RecurringTask recurringTask) {
        return new RecurringTaskResponse(recurringTask.getId(), recurringTask.getRecurrencePattern());
    }
}
