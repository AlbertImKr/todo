package me.albert.todo.service.dto.response;

import java.time.Duration;
import java.time.Period;
import java.util.List;
import me.albert.todo.domain.Tag;
import me.albert.todo.domain.Todo;

public record TodoDetailResponse(
        Long id,
        String title,
        String description,
        String dueDate,
        String createdAt,
        String updatedAt,
        String status,
        String priority,
        String project,
        Period recurringTask,
        List<Duration> notificationSettings,
        List<String> tags
) {

    public static TodoDetailResponse from(Todo todo) {
        return new TodoDetailResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getDueDate().toString(),
                todo.getCreatedAt().toString(),
                todo.getUpdatedAt().toString(),
                todo.getStatus().name(),
                todo.getPriority().name(),
                todo.getProject() != null ? todo.getProject().getName() : null,
                todo.getRecurringTask() != null ? todo.getRecurringTask().getRecurrencePattern() : null,
                todo.getNotificationSettings(),
                todo.getTags().stream().map(Tag::getName).toList()
        );
    }

}
