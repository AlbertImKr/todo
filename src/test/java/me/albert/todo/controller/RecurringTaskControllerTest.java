package me.albert.todo.controller;

import static me.albert.todo.controller.AccountSteps.getAccessToken;
import static me.albert.todo.controller.RecurringTaskSteps.반복_작업_생성_요청;
import static me.albert.todo.controller.TodoSteps.할일_이이디_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        body.put("recurrencePattern", "PT1H");

        // when
        var response = 반복_작업_생성_요청(body, todoId, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }
}
