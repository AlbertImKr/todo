package me.albert.todo.controller;

import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureSecondAccountAccessToken;
import static me.albert.todo.controller.steps.GroupSteps.그룹_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_생성_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_생성_요청_후_아이디_가져온다;
import static me.albert.todo.controller.steps.GroupSteps.그룹_수정_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_할당_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_할당_해제_요청;
import static me.albert.todo.controller.steps.TodoSteps.할일_이이디_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
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
        accessToken = getFixtureFirstAccountAccessToken();
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

    @DisplayName("그룹에 할 일을 할당 성공 시 200 상태 코드를 반환한다.")
    @Test
    void assign_todos() {
        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        var response = 그룹_생성_요청(body, accessToken);
        var groupId = response.jsonPath().getLong("id");

        var firstTodoId = 할일_이이디_생성_요청(accessToken);
        var secondTodoId = 할일_이이디_생성_요청(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

        // when
        var assignResponse = 그룹_할일_할당_요청(groupId, todoIds, accessToken);

        // then
        assertThat(assignResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹에 할 일을 할당 해제 성공 시 200 상태 코드를 반환한다.")
    @Test
    void unassign_todos() {
        // given
        var groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);

        var firstTodoId = 할일_이이디_생성_요청(accessToken);
        var secondTodoId = 할일_이이디_생성_요청(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
        그룹_할일_할당_요청(groupId, todoIds, accessToken);

        var unassignTodoIds = new HashMap<>();
        unassignTodoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

        // when
        var unassignResponse = 그룹_할일_할당_해제_요청(groupId, unassignTodoIds, accessToken);

        // then
        assertThat(unassignResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 할일 목록 조회 성공 시 200 상태 코드를 반환한다.")
    @Test
    void listGroupTodos() {
        // given
        var groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);

        var firstTodoId = 할일_이이디_생성_요청(accessToken);
        var secondTodoId = 할일_이이디_생성_요청(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
        그룹_할일_할당_요청(groupId, todoIds, accessToken);

        // when
        var response = 그룹_할일_목록_조회_요청(groupId, accessToken);

        // then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.jsonPath().getList("id").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("title").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("content").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("createdAt").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getList("updatedAt").size()).isEqualTo(2)
        );
    }

    @DisplayName("그룹에 포함된 할 일을 다시 할당해도 할 일이 중복되지 않는다.")
    @Test
    void assign_todo_to_group_without_duplicate() {
        // given
        var groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);

        var firstTodoId = 할일_이이디_생성_요청(accessToken);
        var secondTodoId = 할일_이이디_생성_요청(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
        그룹_할일_할당_요청(groupId, todoIds, accessToken);
        그룹_할일_할당_요청(groupId, todoIds, accessToken);

        // when
        var todos = 그룹_할일_목록_조회_요청(groupId, accessToken).jsonPath().getList("id");

        // then
        assertThat(todos.size()).isEqualTo(2);
    }

    @DisplayName("그룹 할일 할당 해제 실패")
    @Nested
    class UnassignTodoFail {

        long groupId;

        @BeforeEach
        void create_group() {
            groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);
        }

        @DisplayName("할 일 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void unassign_todo_without_todo_id() {
            // given
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", List.of());

            // when
            var response = 그룹_할일_할당_해제_요청(groupId, todoIds, accessToken);

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
            var response = 그룹_할일_할당_해제_요청(groupId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 소유주가 아닌 사용자가 할 일을 할당 해제하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void unassign_todo_with_other_user() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();
            var firstTodoId = 할일_이이디_생성_요청(accessToken);
            var secondTodoId = 할일_이이디_생성_요청(accessToken);
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
            그룹_할일_할당_요청(groupId, todoIds, accessToken);

            var unassignTodoIds = new HashMap<>();
            unassignTodoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

            // when
            var response = 그룹_할일_할당_해제_요청(groupId, unassignTodoIds, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @Nested
    @DisplayName("그룹 할당 실패")
    class AssignTodoFail {

        long groupId;

        @BeforeEach
        void create_group() {
            var body = new HashMap<>();
            body.put("name", "group");
            body.put("description", "description");
            var response = 그룹_생성_요청(body, accessToken);
            groupId = response.jsonPath().getLong("id");
        }

        @DisplayName("할 일 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void assign_todo_without_todo_id() {
            // given
            var body = new HashMap<>();
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", List.of());

            // when
            var response = 그룹_할일_할당_요청(groupId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("할 일 ID가 null이면 400 상태 코드를 반환한다.")
        @Test
        void assign_todo_with_null_todo_id() {
            // given
            var body = new HashMap<>();
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", null);

            // when
            var response = 그룹_할일_할당_요청(groupId, todoIds, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 소유주가 아닌 사용자가 할 일을 할당하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void assign_todo_with_other_user() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();
            var body = new HashMap<>();
            var firstTodoId = 할일_이이디_생성_요청(accessToken);
            var secondTodoId = 할일_이이디_생성_요청(accessToken);
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

            // when
            var response = 그룹_할일_할당_요청(groupId, todoIds, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
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
            var otherAccessToken = getFixtureSecondAccountAccessToken();
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
