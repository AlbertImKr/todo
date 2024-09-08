package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("태그로 할 일 조회 쿼리 DTO")
class SearchByTagQueryTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("태그 이름이 유효성 검사를 통과한다")
    @Test
    void search_by_tag_query_is_valid() {
        // given
        var request = new SearchByTagQuery("tag");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }


    @DisplayName("태그 이름이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void tag_name_is_empty() {
        // given
        var request = new SearchByTagQuery("");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("태그 이름이 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void tag_name_is_null() {
        // given
        var request = new SearchByTagQuery(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("태그 이름이 20자를 넘으면 유효성 검사를 통과하지 못한다")
    @Test
    void tag_name_is_over_20() {
        // given
        var request = new SearchByTagQuery("1".repeat(21));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("태그 이름이 20자 이하이면 유효성 검사를 통과한다")
    @Test
    void tag_name_is_less_than_20() {
        // given
        var request = new SearchByTagQuery("1".repeat(20));

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }
}
