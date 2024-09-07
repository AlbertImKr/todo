package me.albert.todo.controller;

import static me.albert.todo.controller.docs.ProjectDocument.assignTodoToProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.createProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.deleteProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.getProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.listProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.unassignTodoFromProjectDocumentation;
import static me.albert.todo.controller.docs.ProjectDocument.updateProjectDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureSecondAccountAccessToken;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_목록_조회_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_삭제_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_생성_및_ID_반환;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_생성_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_수정_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_조회_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_할일_할당_요청;
import static me.albert.todo.controller.steps.ProjectSteps.프로젝트_할일_할당_해제_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_생성_및_ID_반환;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
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

    @DisplayName("프로젝트를 조회한다")
    @Test
    void get_project() {
        // docs
        spec.filter(getProjectDocumentation());

        // given
        var projectId = 프로젝트_생성_및_ID_반환(accessToken);
        var todoId1 = 할일_생성_및_ID_반환(accessToken);
        var todoId2 = 할일_생성_및_ID_반환(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", new Long[]{todoId1, todoId2});
        프로젝트_할일_할당_요청(projectId, todoIds, accessToken);

        // when
        var response = 프로젝트_조회_요청(projectId, accessToken, spec);

        // then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.jsonPath().getLong("id")).isEqualTo(projectId)
        );
    }

    @DisplayName("프로젝트에서 할 일을 해제한다")
    @Test
    void unassign_todo_from_project() {
        // docs
        spec.filter(unassignTodoFromProjectDocumentation());

        // given
        var projectId = 프로젝트_생성_및_ID_반환(accessToken);
        var todoId1 = 할일_생성_및_ID_반환(accessToken);
        var todoId2 = 할일_생성_및_ID_반환(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", new Long[]{todoId1, todoId2});
        프로젝트_할일_할당_요청(projectId, todoIds, accessToken);
        var body = new HashMap<>();
        body.put("todoIds", List.of(todoId1));

        // when
        var response = 프로젝트_할일_할당_해제_요청(projectId, body, accessToken, spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("프로젝트에 할 일을 할당한다")
    @Test
    void assign_todo_to_project() {
        // docs
        spec.filter(assignTodoToProjectDocumentation());

        // given
        var projectId = 프로젝트_생성_및_ID_반환(accessToken);
        var todoId1 = 할일_생성_및_ID_반환(accessToken);
        var todoId2 = 할일_생성_및_ID_반환(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", new Long[]{todoId1, todoId2});

        // when
        var response = 프로젝트_할일_할당_요청(projectId, todoIds, accessToken, spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
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

    @DisplayName("프로젝트 목록을 조회한다")
    @Test
    void get_projects() {
        // docs
        spec.filter(listProjectDocumentation());

        // given
        프로젝트_생성_및_ID_반환(accessToken);
        프로젝트_생성_및_ID_반환(accessToken);

        // when
        var deleteResponse = 프로젝트_목록_조회_요청(accessToken, spec);

        // then
        Assertions.assertAll(
                () -> assertThat(deleteResponse.statusCode()).isEqualTo(200),
                () -> assertThat(deleteResponse.jsonPath().getList("id").size()).isEqualTo(2)
        );
    }

    @DisplayName("프로젝트 할 일 할당 해제 실패 테스트")
    @Nested
    class UnassignTodoFromProjectFail {

        long projectId;

        @BeforeEach
        void setUp() {
            projectId = 프로젝트_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void unassign_todo_without_todo_id() {
            // given
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", new Long[]{});

            // when
            var response = 프로젝트_할일_할당_해제_요청(projectId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 ID가 null이면 400 상태 코드를 반환한다.")
        @Test
        void unassign_todo_with_null_todo_id() {
            // given
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", null);

            // when
            var response = 프로젝트_할일_할당_해제_요청(projectId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("다른 사용자의 프로젝트에서 할 일을 할당 해제하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void unassign_todo_from_other_user_project() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", new Long[]{todoId});
            프로젝트_할일_할당_요청(projectId, todoIds, accessToken);

            // when
            var response = 프로젝트_할일_할당_해제_요청(projectId, todoIds, otherUserAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("프로젝트 할 일 할당 실패 테스트")
    @Nested
    class AssignTodoToProjectFail {

        long projectId;

        @BeforeEach
        void setUp() {
            projectId = 프로젝트_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void assign_todo_without_todo_id() {
            // given
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", new Long[]{});

            // when
            var response = 프로젝트_할일_할당_요청(projectId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 ID가 null이면 400 상태 코드를 반환한다.")
        @Test
        void assign_todo_with_null_todo_id() {
            // given
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", null);

            // when
            var response = 프로젝트_할일_할당_요청(projectId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("다른 사용자의 프로젝트에 할 일을 할당하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void assign_todo_to_other_user_project() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", new Long[]{todoId});

            // when
            var response = 프로젝트_할일_할당_요청(projectId, todoIds, otherUserAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
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
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PROJECT_NAME_NOT_NULL)
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
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PROJECT_NAME_MESSAGE)
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
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PROJECT_NAME_NOT_NULL)
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
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PROJECT_NAME_MESSAGE)
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
                    () -> assertThat(response.body().asString()).contains(ValidationMessages.PROJECT_NAME_NOT_NULL)
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
