package me.albert.todo.service.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 생성 요청 DTO")
class TodoCreateRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("할 일 생성 요청이 유효성 검사를 통과한다")
    @Test
    void todo_create_request_is_valid() {
        // given
        var request = new TodoCreateRequest("title", "description", LocalDateTime.now().plusDays(1));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("할 일 제목이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void title_is_empty() {
        // given
        var request = new TodoCreateRequest("", "description", LocalDateTime.now());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 설명이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void description_is_empty() {
        // given
        var request = new TodoCreateRequest("title", "", LocalDateTime.now());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 마감일이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void due_date_is_empty() {
        // given
        var request = new TodoCreateRequest("title", "description", null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 마감일이 과거일이면 유효성 검사를 통과하지 못한다")
    @Test
    void due_date_is_past() {
        // given
        var request = new TodoCreateRequest("title", "description", LocalDateTime.now().minusDays(1));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 제목이 1자 미만이면 유효성 검사를 통과하지 못한다")
    @Test
    void title_length_is_less_than_1() {
        // given
        var request = new TodoCreateRequest("", "description", LocalDateTime.now());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 제목이 100자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void title_length_is_greater_than_100() {
        // given
        var request = new TodoCreateRequest("a".repeat(101), "description", LocalDateTime.now());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 설명이 1000자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void description_length_is_greater_than_1000() {
        // given
        var request = new TodoCreateRequest("title", "a".repeat(1001), LocalDateTime.now());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 제목과 설명이 모두 유효성 검사를 통과하지 못하면 유효성 검사를 통과하지 못한다")
    @Test
    void title_and_description_are_invalid() {
        // given
        var request = new TodoCreateRequest("", "", LocalDateTime.now());

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
