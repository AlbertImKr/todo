package me.albert.todo.controller;

import static me.albert.todo.controller.steps.AccountSteps.getAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getOtherAccessToken;
import static me.albert.todo.controller.steps.TodoSteps.할일_삭제_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_상태_변경_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_생성_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_수정_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_이이디_생성_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("할 일 관련 기능")
class TodoControllerTest extends TodoAcceptanceTest {

    String accessToken;

    @BeforeEach
    void setUser() {
        accessToken = getAccessToken();
    }

    @DisplayName("할 일 생성 성공 시 201 Created 반환")
    @Test
    void create_todo_if_success() {
        // given
        var dueDate = LocalDateTime.now().plusDays(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        );
        var body = new HashMap<>();
        body.put("title", "할 일 제목");
        body.put("description", "할 일 설명");
        body.put("dueDate", dueDate);

        // when
        var target = 할일_생성_요청(body, accessToken);

        // then
        Assertions.assertAll(
                () -> assertThat(target.statusCode()).isEqualTo(201),
                () -> assertThat(target.jsonPath().getLong("id")).isNotNull()
        );
    }

    @DisplayName("할 일 수정 성공 시 200 OK 반환")
    @Test
    void update_todo_if_success() {
        // given
        var todoId = 할일_이이디_생성_요청(accessToken);
        var dueDate = LocalDateTime.now().plusDays(1).format(
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
        assertThat(target.statusCode()).isEqualTo(200);
    }

    @DisplayName("할 일 삭제 성공 시 204 No Content 반환")
    @Test
    void delete_todo_if_success() {
        // given
        var todoId = 할일_이이디_생성_요청(accessToken);

        // when
        var target = 할일_삭제_요청(todoId, accessToken);

        // then
        assertThat(target.statusCode()).isEqualTo(204);
    }

    @DisplayName("할 일 조회 성공 시 200 OK 반환")
    @Test
    void get_todo_if_success() {
        // given
        var todoId = 할일_이이디_생성_요청(accessToken);

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
        // given
        var todoId = 할일_이이디_생성_요청(accessToken);
        var body = new HashMap<>();
        body.put("status", "COMPLETED");

        // when
        var target = 할일_상태_변경_요청(todoId, body, accessToken);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var target = 할일_생성_요청(body, accessToken);

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
            var todoId = 할일_이이디_생성_요청(accessToken);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 설명이 없으면 400 Bad Request 반환")
        @Test
        void description_is_empty() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("마감일이 현재 시간보다 이전이면 400 Bad Request 반환")
        @Test
        void due_date_is_past() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
            var dueDate = LocalDateTime.now().minusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("마감일이 없으면 400 Bad Request 반환")
        @Test
        void due_date_is_empty() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
            var body = new HashMap<>();
            body.put("title", "할 일 제목");
            body.put("description", "할 일 설명");

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("마감일 형식이 잘못되면 400 Bad Request 반환")
        @Test
        void due_date_is_invalid() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
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
            var todoId = 할일_이이디_생성_요청(accessToken);
            var tooLongTitle = "a".repeat(101);
            var dueDate = LocalDateTime.now().plusDays(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            var body = new HashMap<>();
            body.put("title", tooLongTitle);
            body.put("description", "할 일 설명");
            body.put("dueDate", dueDate);

            // when
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 설명이 1000자를 초과하면 400 Bad Request 반환")
        @Test
        void description_is_too_long() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
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
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 제목과 설명이 모두 유효성 검사를 통과하지 못하면 400 Bad Request 반환")
        @Test
        void title_and_description_are_invalid() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
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
            var target = 할일_수정_요청(body, todoId, accessToken);

            // then
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 상태가 없으면 400 Bad Request 반환")
        @Test
        void status_is_empty() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
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
            assertThat(target.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 상태가 잘못되면 400 Bad Request 반환")
        @Test
        void status_is_invalid() {
            // given
            var todoId = 할일_이이디_생성_요청(accessToken);
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
            todoId = 할일_이이디_생성_요청(accessToken);
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
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }

        @DisplayName("다른 사용자의 할 일을 삭제하려고 하면 할 일을 찾을 수 없으면 404 Not Found 반환")
        @Test
        void delete_other_user_todo() {
            // given
            var otherUserAccessToken = getOtherAccessToken();

            // when
            var target = 할일_삭제_요청(todoId, otherUserAccessToken);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().asString()).contains("할 일을 찾을 수 없습니다.")
            );
        }
    }

    @DisplayName("할 일 조회 실패")
    @Nested
    class GetTodoFail {

        long todoId;

        @BeforeEach
        void setTodo() {
            todoId = 할일_이이디_생성_요청(accessToken);
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
            var otherUserAccessToken = getOtherAccessToken();

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
            todoId = 할일_이이디_생성_요청(accessToken);
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
