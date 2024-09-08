package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("태그 할당 요청 DTO")
class AssignTagRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("태그 ID가 유효성 검사를 통과한다")
    @Test
    void assign_tag_request_is_valid() {
        // given
        var request = new AssignTagRequest(1L);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("태그 ID가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void tag_id_is_empty() {
        // given
        var request = new AssignTagRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("태그 ID가 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void tag_id_is_null() {
        // given
        var request = new AssignTagRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
