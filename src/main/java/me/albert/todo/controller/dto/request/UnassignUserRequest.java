package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UnassignUserRequest(
        @NotBlank(message = "유저 이름은 필수입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "유저 이름은 영문과 숫자만 포함한 5자 이상 20자 이하로 입력해주세요.")
        String username
) {

}
