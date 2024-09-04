package me.albert.todo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRegisterRequest(
        @NotBlank(message = "유저 이름은 필수입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "유저 이름은 영문과 숫자만 포함한 5자 이상 20자 이하로 입력해주세요.")
        String username,
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
        String password,
        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
        String confirmPassword
) {

}
