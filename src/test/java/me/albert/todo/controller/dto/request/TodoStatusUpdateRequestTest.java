package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import me.albert.todo.domain.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 상태 변경 요청 DTO")
class TodoStatusUpdateRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("할 일 상태 변경 요청이 유효성 검사를 통과한다")
    @Test
    void todo_status_update_request_is_valid() {
        // given
        var request = new TodoStatusUpdateRequest(TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("할 일 상태 변경 요청이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void todo_status_is_empty() {
        // given
        var request = new TodoStatusUpdateRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
