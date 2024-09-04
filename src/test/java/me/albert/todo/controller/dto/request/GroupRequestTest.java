package me.albert.todo.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupRequestTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("그룹 이름과 설명이 유효성 검사를 통과한다")
    @Test
    void group_request_is_valid() {
        // given
        var request = new GroupRequest("group", "description");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @DisplayName("그룹 이름이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void group_name_is_empty() {
        // given
        var request = new GroupRequest("", "description");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("그룹 이름이 20자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void group_name_length_is_greater_than_20() {
        // given
        var toolongName = "groupgroupgroupgroupgroupgroupgroupgroupgroupgroup";
        var request = new GroupRequest(toolongName, "description");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("그룹 설명이 없으면 유효성 검사를 통과하지 못한다")
    @Test
    void group_description_is_empty() {
        // given
        var request = new GroupRequest("group", "");

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }

    @DisplayName("그룹 설명이 100자를 초과하면 유효성 검사를 통과하지 못한다")
    @Test
    void group_description_length_is_greater_than_100() {
        // given
        var toolongDescription = """
                                 descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription
                                 """; // 101 characters
        var request = new GroupRequest("group", toolongDescription);

        // when
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();
    }
}

