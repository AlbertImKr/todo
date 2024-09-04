package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record GroupRequest(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{1,20}$")
        String name,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$")
        String description
) {

}
