package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("반복 작업 수정 요청 DTO")
class RecurringTaskUpdateRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("반복 주기가 유효성 검사를 통과한다")
    @Test
    void recurrence_pattern_is_valid() {
        // given
        var request = new RecurringTaskUpdateRequest(Period.ofDays(1));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("반복 주기가 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void recurrence_pattern_is_null() {
        // given
        var request = new RecurringTaskUpdateRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
