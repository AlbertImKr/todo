package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("태그 검색 요청 DTO")
class SearchTagRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("태그 이름이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void name_is_empty() {
        // given
        var request = new SearchTagRequest("");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("태그 이름이 20자를 넘으면 유효성 검사를 통과하지 못한다")
    @Test
    void name_is_longer_than_20() {
        // given
        var request = new SearchTagRequest("a".repeat(21));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("태그 이름이 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void name_is_null() {
        // given
        var request = new SearchTagRequest(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
