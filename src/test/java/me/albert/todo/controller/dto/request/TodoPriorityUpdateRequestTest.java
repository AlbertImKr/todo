package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import me.albert.todo.domain.TodoPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 우선 순위 변경 요청 DTO")
class TodoPriorityUpdateRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("할 일 우선 순위가 유효성 검사를 통과한다")
    @Test
    void todo_priority_update_request_is_valid() {
        // given
        var request = new TodoPriorityUpdateRequest(TodoPriority.HIGH);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("할 일 우선 순위가 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void todo_priority_is_null() {
        // given
        var request = new TodoPriorityUpdateRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
