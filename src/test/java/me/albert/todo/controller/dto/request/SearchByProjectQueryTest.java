package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("프로젝트별 할 일 검색 쿼리 DTO")
class SearchByProjectQueryTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("프로젝트 ID가 유효성 검사를 통과한다")
    @Test
    void search_by_project_query_is_valid() {
        // given
        var request = new SearchByProjectQuery(1L);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("프로젝트 ID가 null이면 유효성 검사를 통과하지 못한다")
    @Test
    void project_id_is_null() {
        // given
        var request = new SearchByProjectQuery(null);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("프로젝트 ID가 음수이면 유효성 검사를 통과하지 못한다")
    @Test
    void project_id_is_negative() {
        // given
        var request = new SearchByProjectQuery(-1L);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
