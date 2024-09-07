package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("프로젝트 수정 요청 DTO")
class ProjectUpdateRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("프로젝트 이름이 유효성 검사를 통과한다")
    @Test
    void project_update_request_is_valid() {
        // given
        var request = new ProjectUpdateRequest("project");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("프로젝트 이름이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void project_name_is_empty() {
        // given
        var request = new ProjectUpdateRequest("");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}
