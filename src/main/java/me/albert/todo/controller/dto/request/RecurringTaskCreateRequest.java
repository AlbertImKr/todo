package me.albert.todo.controller.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Duration;
import me.albert.todo.controller.dto.DurationSerializer;

public record RecurringTaskCreateRequest(
        @JsonSerialize(using = DurationSerializer.class)
        Duration recurrencePattern
) {

}
