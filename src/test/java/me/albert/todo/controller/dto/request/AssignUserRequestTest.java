package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("유저 할당 요청 DTO")
class AssignUserRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("유저 이름이 유효성 검사를 통과한다")
    @Test
    void assign_user_request_is_valid() {
        // given
        var request = new AssignUserRequest("username");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("유저 이름이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void username_is_empty() {
        // given
        var request = new AssignUserRequest("");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("유저 이름에 영문과 숫자 이외의 문자가 있으면 유효성 검사를 통과하지 못한다")
    @Test
    void username_has_special_character() {
        // given
        var request = new AssignUserRequest("username!");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }


    @DisplayName("유저 이름이 20자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void username_is_longer_than_20() {
        // given
        var request = new AssignUserRequest("a".repeat(21));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("유저 이름이 5자 미만이면 유효성 검사를 통과하지 못한다")
    @Test
    void username_is_shorter_than_5() {
        // given
        var request = new AssignUserRequest("a".repeat(4));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
