package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UnassignTodoToGroupRequest(
        @NotEmpty(message = "할 일 ID는 비어있을 수 없습니다.")
        List<Long> todoIds
) {

}
