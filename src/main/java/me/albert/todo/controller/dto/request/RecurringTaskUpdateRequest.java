package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.Period;

public record RecurringTaskUpdateRequest(
        @NotNull(message = "반복 주기는 필수입니다")
        Period recurrencePattern
) {

}
