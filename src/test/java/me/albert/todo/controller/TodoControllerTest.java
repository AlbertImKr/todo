package me.albert.todo.controller;

import static me.albert.todo.controller.AccountSteps.getAccessToken;
import static me.albert.todo.controller.TodoSteps.할일_생성_요청;
import static me.albert.todo.controller.TodoSteps.할일_수정_요청;
import static me.albert.todo.controller.TodoSteps.할일_이이디_생성_요청;
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

    @DisplayName("할일 생성 성공 시 201 Created 반환")
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
}
