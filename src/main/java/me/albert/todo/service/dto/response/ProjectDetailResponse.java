package me.albert.todo.service.dto.response;

import java.util.List;
import me.albert.todo.domain.Project;

public record ProjectDetailResponse(
        Long id,
        String name,
        List<TodoResponse> todos
) {

    public static ProjectDetailResponse from(Project project) {
        return new ProjectDetailResponse(project.getId(), project.getName(), TodoResponse.from(project.getTodos()));
    }
}
