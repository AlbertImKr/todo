package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.time.Duration;
import java.util.List;
import me.albert.todo.utils.ValidationMessages;

public record TodoUpdateNotificationRequest(
        @NotEmpty(message = ValidationMessages.NOTIFICATIONS_SETTING_NOT_NUll_MESSAGE)
        List<Duration> notifyAt
) {

}
