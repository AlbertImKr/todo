package me.albert.todo.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import me.albert.todo.domain.Tag;
import me.albert.todo.domain.Todo;
import me.albert.todo.domain.TodoPriority;

public record TodoResponse(
        Long id,
        String title,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dueDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        String status,
        List<String> tags,
        TodoPriority priority
) {

    public static TodoResponse from(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getDueDate(),
                todo.getCreatedAt(),
                todo.getUpdatedAt(),
                todo.getStatus().name(),
                todo.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList()),
                todo.getPriority()
        );
    }

    public static List<TodoResponse> from(List<Todo> todos) {
        return todos.stream()
                .map(TodoResponse::from)
                .collect(Collectors.toList());
    }
}
