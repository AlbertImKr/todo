package me.albert.todo.controller;

import static me.albert.todo.controller.docs.RecurringTaskDocument.deleteRecurringTaskDocumentation;
import static me.albert.todo.controller.docs.RecurringTaskDocument.updateRecurringTaskDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureSecondAccountAccessToken;
import static me.albert.todo.controller.steps.RecurringTaskSteps.반복_작업_삭제_요청;
import static me.albert.todo.controller.steps.RecurringTaskSteps.반복_작업_업데이트_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_생성_및_ID_반환;
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
        accessToken = getFixtureFirstAccountAccessToken();
        todoId = 할일_생성_및_ID_반환(accessToken);
    }

    @DisplayName("할 일에 반복 작업을 업데이트 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_todo_recurring_task() {
        // docs
        this.spec.filter(updateRecurringTaskDocumentation());

        // given
        var body = new HashMap<>();
        body.put("recurrencePattern", "P1D");

        // when
        var response = 반복_작업_업데이트_요청(body, todoId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @DisplayName("할 일에 반복 작업을 삭제 성공 시 204 상태 코드를 반환한다.")
    @Test
    void delete_todo_recurring_task() {
        // docs
        this.spec.filter(deleteRecurringTaskDocumentation());

        // given
        var body = new HashMap<>();
        body.put("recurrencePattern", "P1D");
        반복_작업_업데이트_요청(body, todoId, accessToken);

        // when
        var response = 반복_작업_삭제_요청(todoId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(204);
    }

    @DisplayName("할 일에 반복 작업 업데이트 실패 테스트")
    @Nested
    class UpdateRecurringTaskFailTest {

        @DisplayName("할 일이 없으면 404 상태 코드를 반환한다.")
        @Test
        void update_recurring_task_fail_with_invalid_todo_id() {
            // given
            var body = new HashMap<>();
            body.put("recurrencePattern", "P1D");
            var invalidTodoId = 0L;

            // when
            var response = 반복_작업_업데이트_요청(body, invalidTodoId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("반복 작업이 다른 사용자의 것이면 403 상태 코드를 반환한다.")
        @Test
        void update_recurring_task_with_different_user() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();
            var body = new HashMap<>();
            body.put("recurrencePattern", "P1D");

            // when
            var response = 반복_작업_업데이트_요청(body, todoId, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("할 일에 반복 작업 삭제 실패 테스트")
    @Nested
    class DeleteRecurringTaskFailTest {

        @DisplayName("할 일이 없으면 404 상태 코드를 반환한다.")
        @Test
        void delete_recurring_task_fail_with_invalid_todo_id() {
            // given
            var invalidTodoId = 0L;

            // when
            var response = 반복_작업_삭제_요청(invalidTodoId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("반복 작업이 다른 사용자의 것이면 403 상태 코드를 반환한다.")
        @Test
        void delete_recurring_task_with_different_user() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var response = 반복_작업_삭제_요청(todoId, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }
}
