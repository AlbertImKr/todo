package me.albert.todo.controller;

import static me.albert.todo.controller.docs.ProjectDocument.createProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.deleteProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.updateProjectDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureSecondAccountAccessToken;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_삭제_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_생성_및_ID_반환;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_생성_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_수정_요청;
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

    @DisplayName("프로젝트를 수정한다")
    @Test
    void update_project() {
        // docs
        spec.filter(updateProjectDocumentation());

        // given
        var projectId = 프로젝트_생성_및_ID_반환(accessToken);
        var updateBody = new HashMap<>();
        updateBody.put("name", "수정된 프로젝트");

        // when
        var updateResponse = 프로젝트_수정_요청(updateBody, projectId, accessToken, spec);

        // then
        assertThat(updateResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("프로젝트를 삭제한다")
    @Test
    void delete_project() {
        // docs
        spec.filter(deleteProjectDocumentation());

        // given
        var projectId = 프로젝트_생성_및_ID_반환(accessToken);

        // when
        var deleteResponse = 프로젝트_삭제_요청(projectId, accessToken, spec);

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(204);
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

    @DisplayName("프로젝트 수정 실패 테스트")
    @Nested
    class UpdateProjectFail {

        long projectId;

        @BeforeEach
        void setUp() {
            projectId = 프로젝트_생성_및_ID_반환(accessToken);
        }

        @DisplayName("프로젝트 이름이 없으면 400 상태 코드를 반환한다.")
        @Test
        void update_project_without_name() {
            // given
            var body = new HashMap<>();
            body.put("name", "");

            // when
            var response = 프로젝트_수정_요청(body, projectId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(400),
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PRO_NAME_NOT_NULL)
            );
        }

        @DisplayName("프로젝트 이름이 20자를 넘으면 400 상태 코드를 반환한다.")
        @Test
        void update_project_with_long_name() {
            // given
            var body = new HashMap<>();
            body.put("name", "프로젝트 이름이 20자를 넘으면 400 상태 코드를 반환한다.");

            // when
            var response = 프로젝트_수정_요청(body, projectId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(400),
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PRO_NAME_MESSAGE)
            );
        }

        @DisplayName("프로젝트 이름이 null이면 400 상태 코드를 반환한다.")
        @Test
        void update_project_with_null_name() {
            // given
            var body = new HashMap<>();
            body.put("name", null);

            // when
            var response = 프로젝트_수정_요청(body, projectId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(400),
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PRO_NAME_NOT_NULL)
            );
        }

        @DisplayName("다른 사용자의 프로젝트를 수정하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void update_other_user_project() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            HashMap<Object, Object> body = new HashMap<>();
            body.put("name", "수정된 프로젝트");

            // when
            var response = 프로젝트_수정_요청(body, projectId, otherUserAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("프로젝트 삭제 실패 테스트")
    @Nested
    class DeleteProjectFail {

        long projectId;

        @BeforeEach
        void setUp() {
            projectId = 프로젝트_생성_및_ID_반환(accessToken);
        }

        @DisplayName("다른 사용자의 프로젝트를 삭제하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void delete_other_user_project() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var response = 프로젝트_삭제_요청(projectId, otherUserAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }

        @DisplayName("존재하지 않는 프로젝트를 삭제하려고 하면 404 상태 코드를 반환한다.")
        @Test
        void delete_not_exist_project() {
            // given
            var notExistProjectId = 100L;

            // when
            var response = 프로젝트_삭제_요청(notExistProjectId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
    }
}
