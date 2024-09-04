package me.albert.todo.service.dto.response;

import java.time.LocalDateTime;
import me.albert.todo.domain.Group;

public record GroupResponse(
        Long id,
        String name,
        String description,
        LocalDateTime updatedAt
) {

    public static GroupResponse from(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getUpdatedAt()
        );
    }
}
