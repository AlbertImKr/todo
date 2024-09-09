package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import me.albert.todo.utils.ValidationMessages;

public record AssignUserToGroupRequest(
        @NotEmpty(message = ValidationMessages.EMPTY_ACCOUNT_IDS)
        List<Long> accountIds
) {

}
