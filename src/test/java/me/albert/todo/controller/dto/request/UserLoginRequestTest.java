package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserLoginRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("유저 이름과 비밀번호가 유효성 검사를 통과한다")
    @Test
    void user_login_request_is_valid() {
        // given
        var request = new UserLoginRequest("username", "Password1!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("유저 이름이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void username_is_empty() {
        // given
        var request = new UserLoginRequest("", "Password1!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("유저 이름에 영문과 숫자 이외의 문자가 있으면 유효성 검사를 통과하지 못한다")
    @Test
    void username_has_special_character() {
        // given
        var request = new UserLoginRequest("username!", "Password1!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("유저 이름이 20자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void username_length_is_greater_than_20() {
        // given
        var request = new UserLoginRequest("usernameusernameusername", "Password1!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("비밀번호가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void password_is_empty() {
        // given
        var request = new UserLoginRequest("username", "");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("비밀번호의 길이가 8자 미만이면 유효성 검사를 통과하지 못한다")
    @Test
    void password_length_is_less_than_8() {
        // given
        var request = new UserLoginRequest("username", "Pass1!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("비밀번호에 영문 대문자가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void password_has_no_uppercase() {
        // given
        var request = new UserLoginRequest("username", "password1!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("비밀번호에 숫자가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void password_has_no_number() {
        // given
        var request = new UserLoginRequest("username", "Password!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("비밀번호에 특수문자가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void password_has_no_special_character() {
        // given
        var request = new UserLoginRequest("username", "Password1");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
