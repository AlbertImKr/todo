package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequest(
        @NotBlank
        @Size(min = 1, max = 20)
        String name,
        @NotBlank
        @Size(min = 1, max = 100)
        String description
) {

}
