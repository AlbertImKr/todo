package me.albert.todo.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import me.albert.todo.domain.TodoStatus;

public record TodoStatusUpdateRequest(
        @NotNull(message = "할 일 상태는 필수입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        TodoStatus status
) {

}
