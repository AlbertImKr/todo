package me.albert.todo.service.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import me.albert.todo.domain.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 수정 요청 DTO")
class TodoUpdateRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("할 일 수정 요청이 유효성 검사를 통과한다")
    @Test
    void todo_update_request_is_valid() {
        // given
        var request = new TodoUpdateRequest(
                "title", "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("할 일 제목이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void title_is_empty() {
        // given
        var request = new TodoUpdateRequest("", "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 설명이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void description_is_empty() {
        // given
        var request = new TodoUpdateRequest("title", "", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 마감일이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void due_date_is_empty() {
        // given
        var request = new TodoUpdateRequest("title", "description", null, TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 상태가 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void status_is_empty() {
        // given
        var request = new TodoUpdateRequest("title", "description", LocalDateTime.now().plusDays(1), null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 제목이 1자 미만이면 유효성 검사를 통과하지 못한다")
    @Test
    void title_length_is_less_than_1() {
        // given
        var request = new TodoUpdateRequest("", "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 제목이 100자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void title_length_is_greater_than_100() {
        // given
        var toolongTitle = "a".repeat(101);
        var request = new TodoUpdateRequest(
                toolongTitle, "description", LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 설명이 1000자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void description_length_is_greater_than_1000() {
        // given
        var toolongDescription = "a".repeat(1001);
        var request = new TodoUpdateRequest(
                "title", toolongDescription, LocalDateTime.now().plusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("할 일 마감일이 과거 시간이면 유효성 검사를 통과하지 못한다")
    @Test
    void due_date_is_past() {
        // given
        var request = new TodoUpdateRequest(
                "title", "description", LocalDateTime.now().minusDays(1), TodoStatus.COMPLETED);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
