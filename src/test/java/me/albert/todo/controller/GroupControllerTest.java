package me.albert.todo.controller;

import static me.albert.todo.controller.steps.AccountSteps.getAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getOtherAccessToken;
import static me.albert.todo.controller.steps.GroupSteps.그룹_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_생성_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_수정_요청;
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

    @DisplayName("그룹 수정 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_group_if_success() {
        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        var response = 그룹_생성_요청(body, accessToken);
        var groupId = response.jsonPath().getLong("id");
        body.put("name", "updated group");
        body.put("description", "updated description");

        // when
        var updateResponse = 그룹_수정_요청(body, groupId, accessToken);

        // then
        assertThat(updateResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 목록 조회 성공 시 200 상태 코드를 반환한다.")
    @Test
    void listGroup() {
        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        그룹_생성_요청(body, accessToken);
        var body2 = new HashMap<>();
        body2.put("name", "group2");
        body2.put("description", "description2");
        그룹_생성_요청(body2, accessToken);

        // when
        var response = 그룹_목록_조회_요청(accessToken);

        // then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.jsonPath().getList("name").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("description").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("id").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("updatedAt").size()).isEqualTo(2)
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

    @Nested
    @DisplayName("그룹 수정 실패")
    class UpdateGroupFail {

        long groupId;

        @BeforeEach
        void create_group() {
            var body = new HashMap<>();
            body.put("name", "group");
            body.put("description", "description");
            var response = 그룹_생성_요청(body, accessToken);
            groupId = response.jsonPath().getLong("id");
        }

        @DisplayName("그룹 이름이 없으면 400 상태 코드를 반환한다.")
        @Test
        void update_group_without_name() {
            // given
            var body = new HashMap<>();
            body.put("description", "description");

            // when
            var response = 그룹_수정_요청(body, groupId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 이름이 20자를 초과하면 400 상태 코드를 반환한다.")
        @Test
        void update_group_with_long_name() {
            // given
            var body = new HashMap<>();
            var toolongName = "toolongNametoolongNam";
            body.put("name", toolongName);
            body.put("description", "description");

            // when
            var response = 그룹_수정_요청(body, groupId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 설명이 없으면 400 상태 코드를 반환한다.")
        @Test
        void update_group_without_description() {
            // given
            var body = new HashMap<>();
            body.put("name", "group");

            // when
            var response = 그룹_수정_요청(body, groupId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 설명이 100자를 초과하면 400 상태 코드를 반환한다.")
        @Test
        void update_group_with_long_description() {
            // given
            var body = new HashMap<>();
            var toolongDescription = """
                                     toolongDescriptiontoolongDescriptiontoolongDescriptiontoolongDescriptiontoolongDescriptiontoolongDesc
                                     """;
            body.put("name", "group");
            body.put("description", toolongDescription);

            // when
            var response = 그룹_수정_요청(body, groupId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 소유주가 아닌 사용자가 그룹을 수정하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void update_group_with_other_user() {
            // given
            var otherAccessToken = getOtherAccessToken();
            var body = new HashMap<>();
            body.put("name", "group");
            body.put("description", "description");

            // when
            var response = 그룹_수정_요청(body, groupId, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }
}
