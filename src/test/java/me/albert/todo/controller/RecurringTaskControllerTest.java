package me.albert.todo.controller;

import static me.albert.todo.controller.steps.AccountSteps.getAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getOtherAccessToken;
import static me.albert.todo.controller.steps.RecurringTaskSteps.반복_작업_삭제_요청;
import static me.albert.todo.controller.steps.RecurringTaskSteps.반복_작업_생성_요청;
import static me.albert.todo.controller.steps.RecurringTaskSteps.반복_작업_수정_요청;
import static me.albert.todo.controller.steps.RecurringTaskSteps.반복_작업_아이디;
import static me.albert.todo.controller.steps.TodoSteps.할일_이이디_생성_요청;
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

    @DisplayName("반복 작업 수정 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_recurring_task() {
        // given
        var id = 반복_작업_아이디(todoId, accessToken);
        var body = new HashMap<>();
        body.put("recurrencePattern", "P2D");

        // when
        var response = 반복_작업_수정_요청(body, id, todoId, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("반복 작업 삭제 성공 시 204 상태 코드를 반환한다.")
    @Test
    void delete_recurring_task() {
        // given
        var recurringTaskId = 반복_작업_아이디(todoId, accessToken);

        // when
        var response = 반복_작업_삭제_요청(todoId, recurringTaskId, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(204);
    }

    @DisplayName("반복 작업 수정 실패 테스트")
    @Nested
    class UpdateRecurringTaskFailTest {

        long recurringTaskId;

        @BeforeEach
        void setUp() {
            recurringTaskId = 반복_작업_아이디(todoId, accessToken);
        }

        @DisplayName("반복 주기가 null이면 400 상태 코드를 반환한다.")
        @Test
        void recurrence_pattern_is_empty() {
            // given
            var body = new HashMap<>();
            body.put("recurrencePattern", null);

            // when
            var response = 반복_작업_수정_요청(body, recurringTaskId, todoId, accessToken);

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
            var response = 반복_작업_수정_요청(body, recurringTaskId, todoId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("반복 작업이 다른 사용자의 것이면 404 상태 코드를 반환한다.")
        @Test
        void update_recurring_task_with_different_user() {
            // given
            var otherAccessToken = getOtherAccessToken();
            var body = new HashMap<>();
            body.put("recurrencePattern", "P1D");

            // when
            var response = 반복_작업_수정_요청(body, recurringTaskId, todoId, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
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

    @DisplayName("반복 작업 삭제 실패 테스트")
    @Nested
    class DeleteRecurringTaskFailTest {

        @DisplayName("반복 작업이 없으면 404 상태 코드를 반환한다.")
        @Test
        void delete_recurring_task_fail() {
            // given
            var recurringTaskId = 0L;

            // when
            var response = 반복_작업_삭제_요청(todoId, recurringTaskId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("반복 작업이 다른 사용자의 것이면 404 상태 코드를 반환한다.")
        @Test
        void delete_recurring_task_with_different_user() {
            // given
            var recurringTaskId = 반복_작업_아이디(todoId, accessToken);
            var otherAccessToken = getOtherAccessToken();

            // when
            var response = 반복_작업_삭제_요청(todoId, recurringTaskId, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
    }
}
