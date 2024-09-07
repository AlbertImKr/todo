package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import me.albert.todo.utils.ValidationConstraints;
import me.albert.todo.utils.ValidationMessages;

public record UserRegisterRequest(
        @NotBlank(message = ValidationMessages.ACCOUNT_USERNAME_NOT_NULL)
        @Pattern(regexp = ValidationConstraints.ACCOUNT_USERNAME_PATTERN,
                message = ValidationMessages.ACCOUNT_USERNAME_MESSAGE)
        String username,
        @NotBlank(message = ValidationMessages.ACCOUNT_PASSWORD_NOT_NULL)
        @Pattern(regexp = ValidationConstraints.ACCOUNT_PASSWORD_PATTERN,
                message = ValidationMessages.ACCOUNT_PASSWORD_MESSAGE)
        String password,
        @NotBlank(message = ValidationMessages.ACCOUNT_CONFIRM_PASSWORD_NOT_NULL)
        @Pattern(regexp = ValidationConstraints.ACCOUNT_PASSWORD_PATTERN,
                message = ValidationMessages.ACCOUNT_CONFIRM_PASSWORD_MESSAGE)
        String confirmPassword
) {

}
