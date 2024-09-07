package me.albert.todo.service.dto.response;

import me.albert.todo.domain.Project;

public record ProjectResponse(
        Long id,
        String name
) {

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(project.getId(), project.getName());
    }
}
