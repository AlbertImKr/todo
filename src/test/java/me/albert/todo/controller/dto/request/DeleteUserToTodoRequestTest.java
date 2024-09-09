package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일에서 사용자 삭제 요청 DTO")
class DeleteUserToTodoRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("사용자 ID가 유효성 검사를 통과한다")
    @Test
    void delete_user_to_todo_request_is_valid() {
        // given
        var request = new DeleteUserToTodoRequest(List.of(1L, 2L));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("사용자 ID가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void user_id_is_empty() {
        // given
        var request = new DeleteUserToTodoRequest(List.of());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("사용자 ID가 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void user_id_is_null() {
        // given
        var request = new DeleteUserToTodoRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
