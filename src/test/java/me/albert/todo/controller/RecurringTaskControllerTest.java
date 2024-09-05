package me.albert.todo.controller;

import static me.albert.todo.controller.AccountSteps.getAccessToken;
import static me.albert.todo.controller.RecurringTaskSteps.반복_작업_생성_요청;
import static me.albert.todo.controller.TodoSteps.할일_이이디_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("반복 작업 관련 인수 테스트")
class RecurringTaskControllerTest extends TodoAcceptanceTest {

    String accessToken;
    long todoId;

    @BeforeEach
    void setUser() {
        accessToken = getAccessToken();
        todoId = 할일_이이디_생성_요청(accessToken);
    }

    @DisplayName("반복 작업 생성 성공 시 201 상태 코드를 반환한다.")
    @Test
    void create_recurring_task() {
        // given
        var body = new HashMap<>();
        body.put("recurrencePattern", "P1D");

        // when
        var response = 반복_작업_생성_요청(body, todoId, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @DisplayName("반복 작업 생성 샐패 테스트")
    @Nested
    class CreateRecurringTaskFailTest {

        @DisplayName("할일이 없으면 404 상태 코드를 반환한다.")
        @Test
        void create_recurring_task_fail() {
            // given
            var body = new HashMap<>();
            body.put("recurrencePattern", "P1D");

            // when
            var response = 반복_작업_생성_요청(body, 0, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("반복 주기가 null이면 400 상태 코드를 반환한다.")
        @Test
        void recurrence_pattern_is_empty() {
            // given
            var body = new HashMap<>();
            body.put("recurrencePattern", null);

            // when
            var response = 반복_작업_생성_요청(body, todoId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("반복 주기 형식이 잘못되면 400 상태 코드를 반환한다.")
        @Test
        void recurrence_pattern_is_invalid() {
            // given
            var body = new HashMap<>();
            body.put("recurrencePattern", "P1");

            // when
            var response = 반복_작업_생성_요청(body, todoId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }
    }
}
