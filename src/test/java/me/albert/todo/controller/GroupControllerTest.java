package me.albert.todo.controller;

import static me.albert.todo.controller.AccountSteps.getAccessToken;
import static me.albert.todo.controller.GroupSteps.그룹_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("그룹 관련 인수 테스트")
class GroupControllerTest extends TodoAcceptanceTest {

    String accessToken;

    @BeforeEach
    void setUser() {
        accessToken = getAccessToken();
    }

    @DisplayName("그룹 생성 성공 시 201 상태 코드를 반환한다.")
    @Test
    void createGroup() {
        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");

        // when
        var response = 그룹_생성_요청(body, accessToken);

        // then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull()
        );
    }

    @Nested
    @DisplayName("그룹 생성 실패")
    class CreateGroupFail {

        @DisplayName("그룹 이름이 없으면 400 상태 코드를 반환한다.")
        @Test
        void createGroupWithoutName() {
            // given
            var body = new HashMap<>();
            body.put("description", "description");

            // when
            var response = 그룹_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 이름에 영문과 숫자 이외의 문자가 있으면 400 상태 코드를 반환한다.")
        @Test
        void createGroupWithSpecialCharacter() {
            // given
            var body = new HashMap<>();
            body.put("name", "group!");
            body.put("description", "description");

            // when
            var response = 그룹_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 이름이 20자를 초과하면 400 상태 코드를 반환한다.")
        @Test
        void createGroupWithLongName() {
            // given
            var body = new HashMap<>();
            var toolongName = "groupgroupgroupgroupgroupgroupgroupgroupgroupgroup";
            body.put("name", toolongName);
            body.put("description", "description");

            // when
            var response = 그룹_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 설명이 없으면 400 상태 코드를 반환한다.")
        @Test
        void createGroupWithoutDescription() {
            // given
            var body = new HashMap<>();
            body.put("name", "group");

            // when
            var response = 그룹_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 설명에 영문과 숫자 이외의 문자가 있으면 400 상태 코드를 반환한다.")
        @Test
        void createGroupWithSpecialCharacterInDescription() {
            // given
            var body = new HashMap<>();
            body.put("name", "group");
            body.put("description", "description!");

            // when
            var response = 그룹_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 설명이 100자를 초과하면 400 상태 코드를 반환한다.")
        @Test
        void createGroupWithLongDescription() {
            // given
            var body = new HashMap<>();
            var toolongDescription = """
                                     descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription
                                     """;
            body.put("name", "group");
            body.put("description", toolongDescription);

            // when
            var response = 그룹_생성_요청(body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }
    }
}
