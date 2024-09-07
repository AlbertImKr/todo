package me.albert.todo.controller;

import static me.albert.todo.controller.docs.ProjectDocument.createProjectDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import me.albert.todo.utils.ValidationMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("프로젝트 인수 테스트")
class ProjectControllerTest extends TodoAcceptanceTest {

    String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = getFixtureFirstAccountAccessToken();
    }

    @DisplayName("프로젝트를 생성한다")
    @Test
    void create_project() {
        // docs
        spec.filter(createProjectDocumentation());

        // given
        var body = new HashMap<>();
        body.put("name", "프로젝트");

        // when
        var target = 프로젝트_생성_요청(body, accessToken, spec);

        // then
        Assertions.assertAll(
                () -> assertThat(target.statusCode()).isEqualTo(201),
                () -> assertThat(target.jsonPath().getLong("id")).isNotNull()
        );
    }

    @DisplayName("프로젝트 생성 실패 테스트")
    @Nested
    class CreateProjectFail {

        @DisplayName("프로젝트 이름이 없으면 400 상태 코드를 반환한다.")
        @Test
        void create_project_without_name() {
            // given
            var body = new HashMap<>();
            body.put("name", "");

            // when
            var response = 프로젝트_생성_요청(body, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(400),
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PRO_NAME_NOT_NULL)
            );
        }

        @DisplayName("프로젝트 이름이 20자를 넘으면 400 상태 코드를 반환한다.")
        @Test
        void create_project_with_long_name() {
            // given
            var body = new HashMap<>();
            body.put("name", "프로젝트 이름이 20자를 넘으면 400 상태 코드를 반환한다.");

            // when
            var response = 프로젝트_생성_요청(body, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(400),
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PRO_NAME_MESSAGE)
            );
        }
    }
}
