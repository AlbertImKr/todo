package me.albert.todo.controller;

import static me.albert.todo.controller.docs.TodoDocument.assignTagToTodoDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.createTodoDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.deleteTodoDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.unassignTagFromTodoDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.updateTodoDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.updateTodoNotificationDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.updateTodoPriorityDocumentation;
import static me.albert.todo.controller.docs.TodoDocument.updateTodoStatusDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.FIXTURE_FIRST_ACCOUNT_USERNAME;
import static me.albert.todo.controller.steps.AccountSteps.FIXTURE_SECOND_ACCOUNT_USERNAME;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureSecondAccountAccessToken;
import static me.albert.todo.controller.steps.TagSteps.태그_생성_및_ID_반환;
import static me.albert.todo.controller.steps.TodoSteps.할일_사용자_할당_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_사용자_할당_해제_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_삭제_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_상태_변경_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_생성_및_ID_반환;
import static me.albert.todo.controller.steps.TodoSteps.할일_생성_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_수정_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_알림_설정_변경_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_우선순위_변경_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_조회_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_태그_할당_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_태그_할당_해제_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import me.albert.todo.TodoAcceptanceTest;
import me.albert.todo.utils.ErrorMessages;
import me.albert.todo.utils.ValidationMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 관련 인수 테스트")
class TodoControllerTest extends TodoAcceptanceTest {

    String accessToken;

    @BeforeEach
    void setUser() {
        accessToken = getFixtureFirstAccountAccessToken();
    }

    @DisplayName("할 일의 알림 설정을 변경 성공 시 200 OK 반환")
    @Test
    void update_todo_notification_if_success() {
        // docs
        spec.filter(updateTodoNotificationDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var body = new HashMap<>();
        body.put("notifyAt", List.of("PT10M", "PT1H"));

        // when
        var target = 할일_알림_설정_변경_요청(todoId, body, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일의 우선 순위 변경 성공 시 200 OK 반환")
    @Test
    void update_todo_priority_if_success() {
        // docs
        spec.filter(updateTodoPriorityDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var body = new HashMap<>();
        body.put("priority", "HIGH");

        // when
        var target = 할일_우선순위_변경_요청(todoId, body, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일에 태그를 해제 성공 시 200 OK 반환")
    @Test
    void unassign_tag_to_todo_if_success() {
        // docs
        spec.filter(unassignTagFromTodoDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var tagId = 태그_생성_및_ID_반환(accessToken, "newTag");
        var assignTagBody = new HashMap<>();
        assignTagBody.put("tagId", tagId);
        할일_태그_할당_요청(todoId, assignTagBody, accessToken);

        // when
        var target = 할일_태그_할당_해제_요청(todoId, tagId, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일에 태그를 할당 성공 시 200 OK 반환")
    @Test
    void assign_tag_to_todo_if_success() {
        // docs
        spec.filter(assignTagToTodoDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var tagId = 태그_생성_및_ID_반환(accessToken, "newTag");
        var assignTagBody = new HashMap<>();
        assignTagBody.put("tagId", tagId);

        // when
        var target = 할일_태그_할당_요청(todoId, assignTagBody, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일 생성 성공 시 201 Created 반환")
    @Test
    void create_todo_if_success() {
        // docs
        spec.filter(createTodoDocumentation());

        // given
        var dueDate = LocalDateTime.now().plusDays(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        );
        var body = new HashMap<>();
        body.put("title", "할 일 제목");
        body.put("description", "할 일 설명");
        body.put("dueDate", dueDate);

        // when
        var target = 할일_생성_요청(body, accessToken, spec);

        // then
        Assertions.assertAll(
                () -> assertThat(target.statusCode()).isEqualTo(201),
                () -> assertThat(target.jsonPath().getLong("id")).isNotNull()
        );
    }

    @DisplayName("할 일 수정 성공 시 200 OK 반환")
    @Test
    void update_todo_if_success() {
        // docs
        spec.filter(updateTodoDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var dueDate = LocalDateTime.now().plusDays(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        );
        var body = new HashMap<>();
        body.put("title", "할 일 제목");
        body.put("description", "할 일 설명");
        body.put("dueDate", dueDate);
        body.put("status", "IN_PROGRESS");

        // when
        var target = 할일_수정_요청(body, todoId, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일 삭제 성공 시 204 No Content 반환")
    @Test
    void delete_todo_if_success() {
        // docs
        spec.filter(deleteTodoDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);

        // when
        var target = 할일_삭제_요청(todoId, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(204);
    }

    @DisplayName("할 일 조회 성공 시 200 OK 반환")
    @Test
    void get_todo_if_success() {
        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);

        // when
        var target = 할일_조회_요청(todoId, accessToken);

        // then
        Assertions.assertAll(
                () -> assertThat(target.statusCode()).isEqualTo(200),
                () -> assertThat(target.jsonPath().getLong("id")).isNotNull(),
                () -> assertThat(target.jsonPath().getString("title")).isNotNull(),
                () -> assertThat(target.jsonPath().getString("description")).isNotNull(),
                () -> assertThat(target.jsonPath().getString("dueDate")).isNotNull(),
                () -> assertThat(target.jsonPath().getString("status")).isNotNull(),
                () -> assertThat(target.jsonPath().getString("createdAt")).isNotNull(),
                () -> assertThat(target.jsonPath().getString("updatedAt")).isNotNull()
        );
    }

    @DisplayName("할 일 상태 변경 성공 시 200 OK 반환")
    @Test
    void update_todo_status_if_success() {
        // docs
        spec.filter(updateTodoStatusDocumentation());

        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var body = new HashMap<>();
        body.put("status", "COMPLETED");

        // when
        var target = 할일_상태_변경_요청(todoId, body, accessToken, spec);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일에 사용자 할당 성공 시 200 OK 반환")
    @Test
    void assign_user_to_todo_if_success() {
        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var assignUserBody = new HashMap<>();
        assignUserBody.put("username", FIXTURE_FIRST_ACCOUNT_USERNAME);

        // when
        var target = 할일_사용자_할당_요청(todoId, assignUserBody, accessToken);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일에 여러 사용자 할당 성공 시 200 OK 반환")
    @Test
    void assign_multiple_users_to_todo_if_success() {
        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var assignUserBody = new HashMap<>();
        assignUserBody.put("username", FIXTURE_FIRST_ACCOUNT_USERNAME);
        getFixtureSecondAccountAccessToken();
        할일_사용자_할당_요청(todoId, assignUserBody, accessToken);
        assignUserBody.put("username", FIXTURE_SECOND_ACCOUNT_USERNAME);

        // when
        var target = 할일_사용자_할당_요청(todoId, assignUserBody, accessToken);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일에 사용자 해제 성공 시 200 OK 반환")
    @Test
    void unassign_user_to_todo_if_success() {
        // given
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var assignUserBody = new HashMap<>();
        assignUserBody.put("username", FIXTURE_FIRST_ACCOUNT_USERNAME);
        할일_사용자_할당_요청(todoId, assignUserBody, accessToken);

        // when
        var target = 할일_사용자_할당_해제_요청(todoId, assignUserBody, accessToken);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일의 알일 설정 변경 실패")
    @Nested
    class UpdateTodoNotificationFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var body = new HashMap<>();
            body.put("notifyAt", List.of("PT10M", "PT1H"));
            var target = 할일_알림_설정_변경_요청(notExistTodoId, body, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }

        @DisplayName("알림 설정이 없으면 400 Bad Request 반환")
        @Test
        void notify_at_is_empty() {
            // given
            var body = new HashMap<>();
            body.put("notifyAt", List.of());

            // when
            var target = 할일_알림_설정_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("알림 설정이 null이면 400 Bad Request 반환")
        @Test
        void notify_at_is_null() {
            // given
            var body = new HashMap<>();
            body.put("notifyAt", null);

            // when
            var target = 할일_알림_설정_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("알림 설정이 잘못된 값이면 400 Bad Request 반환")
        @Test
        void notify_at_is_invalid() {
            // given
            var body = new HashMap<>();
            body.put("notifyAt", List.of("INVALID"));

            // when
            var target = 할일_알림_설정_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("일림 설정을 변결할 권한이 없으면 403 Forbidden 반환")
        @Test
        void no_permission() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var body = new HashMap<>();
            body.put("notifyAt", List.of("PT10M", "PT1H"));

            // when
            var target = 할일_알림_설정_변경_요청(todoId, body, otherUserAccessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("할 일의 우선 순위 변경 실패")
    @Nested
    class UpdateTodoPriorityFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var body = new HashMap<>();
            body.put("priority", "HIGH");
            var target = 할일_우선순위_변경_요청(notExistTodoId, body, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }

        @DisplayName("할 일의 우선 순위가 없으면 400 Bad Request 반환")
        @Test
        void priority_is_empty() {
            // given
            var body = new HashMap<>();
            body.put("priority", "");

            // when
            var target = 할일_우선순위_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일의 우선 순위가 null이면 400 Bad Request 반환")
        @Test
        void priority_is_null() {
            // given
            var body = new HashMap<>();
            body.put("priority", null);

            // when
            var target = 할일_우선순위_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일의 우선 순위가 잘못된 값이면 400 Bad Request 반환")
        @Test
        void priority_is_invalid() {
            // given
            var body = new HashMap<>();
            body.put("priority", "INVALID");

            // when
            var target = 할일_우선순위_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }
    }

    @DisplayName("할 일에 태그를 해제 실패")
    @Nested
    class UnassignTagToTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var target = 할일_태그_할당_해제_요청(notExistTodoId, 1L, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }

        @DisplayName("다른 사용자의 할 일에 태그를 해제하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void unassign_tag_to_other_user_todo() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var tagId = 태그_생성_및_ID_반환(otherUserAccessToken, "newTag");

            // when
            var target = 할일_태그_할당_해제_요청(todoId, tagId, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }
    }

    @DisplayName("할 일에 태그를 할당 실패")
    @Nested
    class AssignTagToTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var assignTagBody = new HashMap<>();
            assignTagBody.put("tagId", 1L);
            var target = 할일_태그_할당_요청(notExistTodoId, assignTagBody, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }

        @DisplayName("다른 사용자의 할 일에 태그를 할당하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void assign_tag_to_other_user_todo() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var assignTagBody = new HashMap<>();
            assignTagBody.put("tagId", 1L);

            // when
            var target = 할일_태그_할당_요청(todoId, assignTagBody, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }

        @DisplayName("태그 ID가 없으면 400 Bad Request 반환")
        @Test
        void tag_id_is_empty() {
            // given
            var assignTagBody = new HashMap<>();
            assignTagBody.put("tagId", null);

            // when
            var target = 할일_태그_할당_요청(todoId, assignTagBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("태그 ID가 0이면 400 Bad Request 반환")
        @Test
        void tag_id_is_zero() {
            // given
            var assignTagBody = new HashMap<>();
            assignTagBody.put("tagId", 0L);

            // when
            var target = 할일_태그_할당_요청(todoId, assignTagBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("태그 ID가 음수이면 400 Bad Request 반환")
        @Test
        void tag_id_is_negative() {
            // given
            var assignTagBody = new HashMap<>();
            assignTagBody.put("tagId", -1L);

            // when
            var target = 할일_태그_할당_요청(todoId, assignTagBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }
    }

    @Nested
    @DisplayName("할 일에 사용자 해제 실패")
    class UnassignUserToTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var unassignUserBody = new HashMap<>();
            unassignUserBody.put("username", "newUser");
            var target = 할일_사용자_할당_해제_요청(notExistTodoId, unassignUserBody, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("다른 사용자의 할 일에 사용자를 해제하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void unassign_user_to_other_user_todo() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var unassignUserBody = new HashMap<>();
            unassignUserBody.put("username", "newUser");

            // when
            var target = 할일_사용자_할당_해제_요청(todoId, unassignUserBody, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("사용자 이름이 없으면 400 Bad Request 반환")
        @Test
        void username_is_empty() {
            // given
            var unassignUserBody = new HashMap<>();
            unassignUserBody.put("username", "");

            // when
            var target = 할일_사용자_할당_해제_요청(todoId, unassignUserBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("사용자 이름에 영문과 숫자 이외의 문자가 있으면 400 Bad Request 반환")
        @Test
        void username_has_special_character() {
            // given
            var unassignUserBody = new HashMap<>();
            unassignUserBody.put("username", "newUser!");

            // when
            var target = 할일_사용자_할당_해제_요청(todoId, unassignUserBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("사용자 이름이 존재하지 않으면 404 Bad Request 반환")
        @Test
        void username_not_exist() {
            // given
            var unassignUserBody = new HashMap<>();
            unassignUserBody.put("username", "notExistUser");

            // when
            var target = 할일_사용자_할당_해제_요청(todoId, unassignUserBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(404);
        }
    }

    @Nested
    @DisplayName("할 일에 사용자 할당 실패")
    class AssignUserToTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var assignUserBody = new HashMap<>();
            assignUserBody.put("username", "newUser");
            var target = 할일_사용자_할당_요청(notExistTodoId, assignUserBody, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("다른 사용자의 할 일에 사용자를 할당하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void assign_user_to_other_user_todo() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();
            var assignUserBody = new HashMap<>();
            assignUserBody.put("username", "newUser");

            // when
            var target = 할일_사용자_할당_요청(todoId, assignUserBody, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("사용자 이름이 없으면 400 Bad Request 반환")
        @Test
        void username_is_empty() {
            // given
            var assignUserBody = new HashMap<>();
            assignUserBody.put("username", "");

            // when
            var target = 할일_사용자_할당_요청(todoId, assignUserBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("사용자 이름에 영문과 숫자 이외의 문자가 있으면 400 Bad Request 반환")
        @Test
        void username_has_special_character() {
            // given
            var assignUserBody = new HashMap<>();
            assignUserBody.put("username", "newUser!");

            // when
            var target = 할일_사용자_할당_요청(todoId, assignUserBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("사용자 이름이 존재하지 않으면 404 Bad Request 반환")
        @Test
        void username_not_exist() {
            // given
            var assignUserBody = new HashMap<>();
            assignUserBody.put("username", "notExistUser");

            // when
            var target = 할일_사용자_할당_요청(todoId, assignUserBody, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(404);
        }
    }

    @Nested
    @DisplayName("할 일 생성 실패")
    class CreateTodoFail {

        @DisplayName("할 일 제목이 없으면 400 Bad Request 반환")
        @Test
        void title_is_empty() {
            // given
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 설명이 없으면 400 Bad Request 반환")
        @Test
        void description_is_empty() {
            // given
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("마감일이 현재 시간보다 이전이면 400 Bad Request 반환")
        @Test
        void due_date_is_past() {
            // given
            var dueDate = LocalDateTime.now().minusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("마감일이 없으면 400 Bad Request 반환")
        @Test
        void due_date_is_empty() {
            // given
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("마감일 형식이 잘못되면 400 Bad Request 반환")
        @Test
        void due_date_is_invalid() {
            // given
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", "2021-01-01");

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 제목이 100자를 초과하면 400 Bad Request 반환")
        @Test
        void title_is_too_long() {
            // given
            var tooLongTitle = "a".repeat(101);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", tooLongTitle);
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 설명이 1000자를 초과하면 400 Bad Request 반환")
        @Test
        void description_is_too_long() {
            // given
            var tooLongDescription = "a".repeat(1001);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", tooLongDescription);
            body.put("dueDate", dueDate);

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 제목과 설명이 모두 유효성 검사를 통과하지 못하면 400 Bad Request 반환")
        @Test
        void title_and_description_are_invalid() {
            // given
            var tooLongTitle = "a".repeat(101);
            var tooLongDescription = "a".repeat(1001);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", tooLongTitle);
            body.put("description", tooLongDescription);
            body.put("dueDate", dueDate);

            // when
            var target = 할일_생성_요청(body, accessToken, spec);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }
    }

    @Nested
    @DisplayName("할 일 수정 실패")
    class UpdateTodoFail {

        @DisplayName("할 일 제목이 없으면 400 Bad Request 반환")
        @Test
        void title_is_empty() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);
            body.put("status", "IN_PROGRESS");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_TITLE_MESSAGE)
            );
        }

        @DisplayName("할 일 설명이 없으면 400 Bad Request 반환")
        @Test
        void description_is_empty() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "");
            body.put("dueDate", dueDate);
            body.put("status", "IN_PROGRESS");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_DESCRIPTION_NOT_NULL)
            );
        }

        @DisplayName("마감일이 현재 시간보다 이전이면 400 Bad Request 반환")
        @Test
        void due_date_is_past() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var dueDate = LocalDateTime.now().minusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);
            body.put("status", "IN_PROGRESS");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_DUE_DATE_FUTURE)
            );
        }

        @DisplayName("마감일이 없으면 400 Bad Request 반환")
        @Test
        void due_date_is_empty() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", "");
            body.put("status", "IN_PROGRESS");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_DUE_DATE_NOT_NULL)
            );
        }

        @DisplayName("마감일 형식이 잘못되면 400 Bad Request 반환")
        @Test
        void due_date_is_invalid() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", "2021-01-01");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 제목이 100자를 초과하면 400 Bad Request 반환")
        @Test
        void title_is_too_long() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var tooLongTitle = "a".repeat(101);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", tooLongTitle);
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);
            body.put("status", "IN_PROGRESS");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_TITLE_MESSAGE)
            );
        }

        @DisplayName("할 일 설명이 1000자를 초과하면 400 Bad Request 반환")
        @Test
        void description_is_too_long() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var tooLongDescription = "a".repeat(1001);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", tooLongDescription);
            body.put("dueDate", dueDate);

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_DESCRIPTION_MESSAGE)
            );
        }

        @DisplayName("할 일 제목과 설명이 모두 유효성 검사를 통과하지 못하면 400 Bad Request 반환")
        @Test
        void title_and_description_are_invalid() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var tooLongTitle = "a".repeat(101);
            var tooLongDescription = "a".repeat(1001);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", tooLongTitle);
            body.put("description", tooLongDescription);
            body.put("dueDate", dueDate);
            body.put("status", "IN_PROGRESS");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_TITLE_MESSAGE)
            );
        }

        @DisplayName("할 일 상태가 없으면 400 Bad Request 반환")
        @Test
        void status_is_empty() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message"))
                            .contains(ValidationMessages.TODO_STATUS_NOT_NULL)
            );
        }

        @DisplayName("할 일 상태가 잘못되면 400 Bad Request 반환")
        @Test
        void status_is_invalid() {
            // given
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);
            body.put("status", "INVALID");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }
    }

    @DisplayName("할 일 삭제 실패")
    @Nested
    class DeleteTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var target = 할일_삭제_요청(notExistTodoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_NOT_FOUND)
            );
        }

        @DisplayName("다른 사용자의 할 일을 삭제하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void delete_other_user_todo() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var target = 할일_삭제_요청(todoId, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(403),
                    () -> assertThat(target.body().asString()).contains(ErrorMessages.TODO_DELETE_NOT_ALLOWED)
            );
        }
    }

    @DisplayName("할 일 조회 실패")
    @Nested
    class GetTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void todo_not_found() {
            // when
            var notExistTodoId = 100L;
            var target = 할일_조회_요청(notExistTodoId, accessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("다른 사용자의 할 일을 조회하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void get_other_user_todo() {
            // given
            var otherUserAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var target = 할일_조회_요청(todoId, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("인증 정보가 없으면 401 Unauthorized 반환")
        @Test
        void unauthorized() {
            // when
            var target = 할일_조회_요청(todoId, "");

            // then
            assertThat(target.statusCode()).isEqualTo(401);
        }
    }

    @Nested
    @DisplayName("할 일 상태 변경 실패")
    class UpdateTodoStatusFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_생성_및_ID_반환(accessToken);
        }

        @DisplayName("할 일 상태가 없으면 400 Bad Request 반환")
        @Test
        void status_is_empty() {
            // given
            var body = new HashMap<>();

            // when
            var target = 할일_상태_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 상태가 잘못되면 400 Bad Request 반환")
        @Test
        void status_is_invalid() {
            // given
            var body = new HashMap<>();
            body.put("status", "INVALID");

            // when
            var target = 할일_상태_변경_요청(todoId, body, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }
    }
}
