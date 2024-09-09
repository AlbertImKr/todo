package me.albert.todo.controller;

import static me.albert.todo.controller.docs.GroupDocument.addRepeatSettingToGroupTodoDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.addUserToGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.assignMembersToGroupTodoDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.assignTagToGroupTodoDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.assignTodoToGroupProjectDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.assignTodosToGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.createGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.createGroupProjectDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.deleteGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.deleteGroupProjectDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.listGroupProjectTodosDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.listGroupUsersDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.removeUsersFromGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.unassignMembersToGroupTodoDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.unassignTagFromGroupTodoDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.unassignTodoFromGroupProjectDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.unassignTodosToGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.updateGroupDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.updateGroupProjectDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.updateGroupTodoDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.updateGroupTodoPriorityDocumentation;
import static me.albert.todo.controller.docs.GroupDocument.updateGroupTodoStatusDocumentation;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureFirstAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.getFixtureSecondAccountAccessToken;
import static me.albert.todo.controller.steps.AccountSteps.로그인_요청;
import static me.albert.todo.controller.steps.AccountSteps.유저_가입_및_ID_반환;
import static me.albert.todo.controller.steps.AccountSteps.화원_가입_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_사용자_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_사용자_제거_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_사용자_추가_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_삭제_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_생성_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_생성_요청_후_아이디_가져온다;
import static me.albert.todo.controller.steps.GroupSteps.그룹_생성및_ID_반환;
import static me.albert.todo.controller.steps.GroupSteps.그룹_수정_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_프로젝트_삭제;
import static me.albert.todo.controller.steps.GroupSteps.그룹_프로젝트_생성;
import static me.albert.todo.controller.steps.GroupSteps.그룹_프로젝트_수정;
import static me.albert.todo.controller.steps.GroupSteps.그룹_프로젝트_할일_할당_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_프로젝트_할일_할당_해제_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_프로젝트별_할일_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_목록_조회_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_반복_설정_추가_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_상태_수정_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_수정_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_우선순위_수정_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_태그_할당_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_태그_할당_해제_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_할당_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일_할당_해제_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일에_할당된_멤버_취소_요청;
import static me.albert.todo.controller.steps.GroupSteps.그룹_할일을_멥버에게_할당_요청;
import static me.albert.todo.controller.steps.TagSteps.태그_생성_및_ID_반환;
import static me.albert.todo.controller.steps.TodoSteps.할일_생성_및_ID_반환;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @DisplayName("그룹 할일에 반복 설정을 추가 성공 시 200 상태 코드를 반환한다.")
    @Test
    void add_repeat_setting_to_group_todo() {
        // docs
        this.spec.filter(addRepeatSettingToGroupTodoDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        var repeatSetting = new HashMap<>();
        repeatSetting.put("recurrencePattern", "P1D");

        // when
        var response = 그룹_할일_반복_설정_추가_요청(groupId, todoId, repeatSetting, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 프로젝트의 할 일 목록 조회 성공 시 200 상태 코드를 반환한다.")
    @Test
    void list_group_project_todos() {
        // docs
        this.spec.filter(listGroupProjectTodosDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var projectId = 그룹_프로젝트_생성(groupId, "project", accessToken).jsonPath().getLong("id");
        var tagsId = new ArrayList<Long>();
        for (int j = 0; j < 3; j++) {
            tagsId.add(태그_생성_및_ID_반환(accessToken, "tag" + j));
        }
        var accountIds = new ArrayList<Long>();
        for (int i = 0; i < 3; i++) {
            accountIds.add(유저_가입_및_ID_반환("newUser" + i));
        }
        for (int i = 0; i < 3; i++) {
            var todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
            그룹_프로젝트_할일_할당_요청(groupId, projectId, List.of(todoId), accessToken);
            for (int j = 0; j < 3; j++) {
                그룹_할일_태그_할당_요청(groupId, todoId, tagsId.get(j), accessToken);
            }
            그룹_할일을_멥버에게_할당_요청(groupId, todoId, accountIds, accessToken);
        }

        // when
        var response = 그룹_프로젝트별_할일_목록_조회_요청(groupId, projectId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 프로젝트에 할 일을 할당 해제 성공 시 200 상태 코드를 반환한다.")
    @Test
    void unassign_todo_from_group_project() {
        // docs
        this.spec.filter(unassignTodoFromGroupProjectDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var projectId = 그룹_프로젝트_생성(groupId, "project", accessToken).jsonPath().getLong("id");
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_프로젝트_할일_할당_요청(groupId, projectId, List.of(todoId), accessToken);

        // when
        var response = 그룹_프로젝트_할일_할당_해제_요청(groupId, projectId, List.of(todoId), accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 프로젝트에 할 일을 할당 성공 시 200 상태 코드를 반환한다.")
    @Test
    void assign_todo_to_group_project() {
        // docs
        this.spec.filter(assignTodoToGroupProjectDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var projectId = 그룹_프로젝트_생성(groupId, "project", accessToken).jsonPath().getLong("id");
        var todoId = 할일_생성_및_ID_반환(accessToken);

        // when
        var response = 그룹_프로젝트_할일_할당_요청(groupId, projectId, List.of(todoId), accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 프로젝트를 삭제 성공 시 204 상태 코드를 반환한다.")
    @Test
    void delete_group_project() {
        // docs
        this.spec.filter(deleteGroupProjectDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var projectId = 그룹_프로젝트_생성(groupId, "project", accessToken).jsonPath().getLong("id");

        // when
        var response = 그룹_프로젝트_삭제(groupId, projectId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(204);
    }

    @DisplayName("그룹 프로젝트를 업데이트 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_group_project() {
        // docs
        this.spec.filter(updateGroupProjectDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var projectId = 그룹_프로젝트_생성(groupId, "project", accessToken).jsonPath().getLong("id");

        // when
        var response = 그룹_프로젝트_수정(groupId, projectId, "updated project", accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 프로젝트를 생성 성공 시 201 상태 코드를 반환한다.")
    @Test
    void create_group_project() {
        // docs
        this.spec.filter(createGroupProjectDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);

        // when
        var response = 그룹_프로젝트_생성(groupId, "project", accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @DisplayName("그룹 할일에 할당된 태그를 제거 성공 시 200 상태 코드를 반환한다.")
    @Test
    void unassign_tag_from_group_todo() {
        // docs
        this.spec.filter(unassignTagFromGroupTodoDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        var tagId = 태그_생성_및_ID_반환(accessToken, "tag");
        그룹_할일_태그_할당_요청(groupId, todoId, tagId, accessToken);

        // when
        var response = 그룹_할일_태그_할당_해제_요청(groupId, todoId, tagId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 할일에 태그를 할당 성공 시 200 상태 코드를 반환한다.")
    @Test
    void assign_tag_to_group_todo() {
        // docs
        this.spec.filter(assignTagToGroupTodoDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        var tagId = 태그_생성_및_ID_반환(accessToken, "tag");

        // when
        var response = 그룹_할일_태그_할당_요청(groupId, todoId, tagId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 할일의 우선순위를 수정 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_group_todo_priority() {
        // docs
        this.spec.filter(updateGroupTodoPriorityDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);

        // when
        var response = 그룹_할일_우선순위_수정_요청(groupId, todoId, "HIGH", accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 할일의 상태를 수정 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_group_todo_status() {
        // docs
        this.spec.filter(updateGroupTodoStatusDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        var updateTodoBody = new HashMap<>();
        updateTodoBody.put("status", "IN_PROGRESS");

        // when
        var response = 그룹_할일_상태_수정_요청(groupId, todoId, updateTodoBody, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 할일을 수정 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_group_todo() {
        // docs
        this.spec.filter(updateGroupTodoDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var todoId = 할일_생성_및_ID_반환(accessToken);
        var dueDate = LocalDateTime.now().plusDays(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        );
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        var updateTodoBody = new HashMap<>();
        updateTodoBody.put("title", "할 일 제목");
        updateTodoBody.put("description", "할 일 설명");
        updateTodoBody.put("dueDate", dueDate);
        updateTodoBody.put("status", "IN_PROGRESS");

        // when
        var response = 그룹_할일_수정_요청(groupId, todoId, updateTodoBody, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹의 할 일에 할당된 멤버를 제거 성공 시 200 상태 코드를 반환한다.")
    @Test
    void unassign_todo_from_group_member() {
        // docs
        this.spec.filter(unassignMembersToGroupTodoDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var memberId = 유저_가입_및_ID_반환("newUser1");
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_사용자_추가_요청(groupId, List.of(memberId), accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        그룹_할일을_멥버에게_할당_요청(groupId, todoId, List.of(memberId), accessToken);

        // when
        var response = 그룹_할일에_할당된_멤버_취소_요청(groupId, todoId, List.of(memberId), accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹의 할 일을 멤버에게 할당 성공 시 200 상태 코드를 반환한다.")
    @Test
    void assign_todo_to_group_member() {
        // docs
        this.spec.filter(assignMembersToGroupTodoDocumentation());

        // given
        var groupId = 그룹_생성및_ID_반환("group", accessToken);
        var memberId = 유저_가입_및_ID_반환("newUser1");
        var todoId = 할일_생성_및_ID_반환(accessToken);
        그룹_사용자_추가_요청(groupId, List.of(memberId), accessToken);
        그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);

        // when
        var response = 그룹_할일을_멥버에게_할당_요청(groupId, todoId, List.of(memberId), accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹에 포함된 유저 목록 조회 성공 시 200 상태 코드를 반환한다.")
    @Test
    void list_group_users() {
        // docs
        this.spec.filter(listGroupUsersDocumentation());

        // given
        var createGroupBody = new HashMap<>();
        createGroupBody.put("name", "group");
        createGroupBody.put("description", "description");
        var groupId = 그룹_생성_요청(createGroupBody, accessToken).jsonPath().getLong("id");
        var accountIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            var createUserBody = new HashMap<>();
            createUserBody.put("username", "newUser" + i);
            createUserBody.put("password", "Password" + i + "!");
            createUserBody.put("confirmPassword", "Password" + i + "!");
            var accountId = 화원_가입_요청(createUserBody).jsonPath().getLong("id");
            accountIds.add(accountId);
        }
        var addUsersToGroupBody = new HashMap<>();
        addUsersToGroupBody.put("accountIds", accountIds);
        그룹_사용자_추가_요청(groupId, addUsersToGroupBody, accessToken);
        var removeUsersFromGroupBody = new HashMap<>();
        removeUsersFromGroupBody.put("accountIds", List.of(accountIds.get(0)));

        // when
        var response = 그룹_사용자_목록_조회_요청(groupId, accessToken, this.spec);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹에 사용자를 제거 성공 시 200 상태 코드를 반환한다.")
    @Test
    void remove_user_from_group() {
        // docs
        this.spec.filter(removeUsersFromGroupDocumentation());

        // given
        var createGroupBody = new HashMap<>();
        createGroupBody.put("name", "group");
        createGroupBody.put("description", "description");
        var response = 그룹_생성_요청(createGroupBody, accessToken);
        var groupId = response.jsonPath().getLong("id");
        var accountIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            var createUserBody = new HashMap<>();
            createUserBody.put("username", "newUser" + i);
            createUserBody.put("password", "Password" + i + "!");
            createUserBody.put("confirmPassword", "Password" + i + "!");
            var accountId = 화원_가입_요청(createUserBody).jsonPath().getLong("id");
            accountIds.add(accountId);
        }
        var addUsersToGroupBody = new HashMap<>();
        addUsersToGroupBody.put("accountIds", accountIds);
        그룹_사용자_추가_요청(groupId, addUsersToGroupBody, accessToken);
        var removeUsersFromGroupBody = new HashMap<>();
        removeUsersFromGroupBody.put("accountIds", List.of(accountIds.get(0)));

        // when
        var removeResponse = 그룹_사용자_제거_요청(groupId, removeUsersFromGroupBody, accessToken, this.spec);

        // then
        assertThat(removeResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹에 사용자를 추가 성공 시 200 상태 코드를 반환한다.")
    @Test
    void add_user_to_group() {
        // docs
        this.spec.filter(addUserToGroupDocumentation());

        // given
        var createGroupBody = new HashMap<>();
        createGroupBody.put("name", "group");
        createGroupBody.put("description", "description");
        var response = 그룹_생성_요청(createGroupBody, accessToken);
        var groupId = response.jsonPath().getLong("id");
        var accountIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            var createUserBody = new HashMap<>();
            createUserBody.put("username", "newUser" + i);
            createUserBody.put("password", "Password" + i + "!");
            createUserBody.put("confirmPassword", "Password" + i + "!");
            var accountId = 화원_가입_요청(createUserBody).jsonPath().getLong("id");
            accountIds.add(accountId);
        }
        var body = new HashMap<>();
        body.put("accountIds", accountIds);

        // when
        var assignResponse = 그룹_사용자_추가_요청(groupId, body, accessToken, this.spec);

        // then
        assertThat(assignResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 삭제 성공 시 204 상태 코드를 반환한다.")
    @Test
    void delete_group() {
        // docs
        this.spec.filter(deleteGroupDocumentation());

        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        var groupId = 그룹_생성_요청(body, accessToken).jsonPath().getLong("id");

        // when
        var deleteResponse = 그룹_삭제_요청(groupId, accessToken, this.spec);

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(204);
    }

    @DisplayName("그룹 생성 성공 시 201 상태 코드를 반환한다.")
    @Test
    void createGroup() {
        // docs
        this.spec.filter(createGroupDocumentation());

        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");

        // when
        var response = 그룹_생성_요청(body, accessToken, this.spec);

        // then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull()
        );
    }

    @DisplayName("그룹 수정 성공 시 200 상태 코드를 반환한다.")
    @Test
    void update_group_if_success() {
        // docs
        this.spec.filter(updateGroupDocumentation());

        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        var response = 그룹_생성_요청(body, accessToken);
        var groupId = response.jsonPath().getLong("id");
        body.put("name", "updated group");
        body.put("description", "updated description");

        // when
        var updateResponse = 그룹_수정_요청(body, groupId, accessToken, this.spec);

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

    @DisplayName("그룹 소유자가 할 일을 할당 성공 시 200 상태 코드를 반환한다.")
    @Test
    void owner_assign_todos_to_group_if_success() {
        // docs
        this.spec.filter(assignTodosToGroupDocumentation());

        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        var response = 그룹_생성_요청(body, accessToken);
        var groupId = response.jsonPath().getLong("id");

        var firstTodoId = 할일_생성_및_ID_반환(accessToken);
        var secondTodoId = 할일_생성_및_ID_반환(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

        // when
        var assignResponse = 그룹_할일_할당_요청(groupId, todoIds, accessToken, this.spec);

        // then
        assertThat(assignResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 멤버가 할 일을 할당 성공 시 200 상태 코드를 반환한다.")
    @Test
    void member_assign_todos_to_group_if_success() {
        // given
        var body = new HashMap<>();
        body.put("name", "group");
        body.put("description", "description");
        var response = 그룹_생성_요청(body, accessToken);
        var groupId = response.jsonPath().getLong("id");
        var signupBody = new HashMap<>();
        signupBody.put("username", "newUser1");
        signupBody.put("password", "Password1!");
        signupBody.put("confirmPassword", "Password1!");
        var memberId = 화원_가입_요청(signupBody).jsonPath().getLong("id");
        var loginBody = new HashMap<>();
        loginBody.put("username", "newUser");
        loginBody.put("password", "Password1!");
        var memberAccessToken = 로그인_요청(loginBody).jsonPath().getString("accessToken");

        var firstTodoId = 할일_생성_및_ID_반환(memberAccessToken);
        var secondTodoId = 할일_생성_및_ID_반환(memberAccessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
        var addMemberBody = new HashMap<>();
        addMemberBody.put("accountIds", List.of(memberId));
        그룹_사용자_추가_요청(groupId, addMemberBody, accessToken);

        // when
        var assignResponse = 그룹_할일_할당_요청(groupId, todoIds, memberAccessToken);

        // then
        assertThat(assignResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹에 할 일을 할당 해제 성공 시 200 상태 코드를 반환한다.")
    @Test
    void unassign_todos() {
        // docs
        this.spec.filter(unassignTodosToGroupDocumentation());

        // given
        var groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);

        var firstTodoId = 할일_생성_및_ID_반환(accessToken);
        var secondTodoId = 할일_생성_및_ID_반환(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
        그룹_할일_할당_요청(groupId, todoIds, accessToken);

        var unassignTodoIds = new HashMap<>();
        unassignTodoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

        // when
        var unassignResponse = 그룹_할일_할당_해제_요청(groupId, unassignTodoIds, accessToken, this.spec);

        // then
        assertThat(unassignResponse.statusCode()).isEqualTo(200);
    }

    @DisplayName("그룹 할일 목록 조회 성공 시 200 상태 코드를 반환한다.")
    @Test
    void listGroupTodos() {
        // given
        var groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);

        var firstTodoId = 할일_생성_및_ID_반환(accessToken);
        var secondTodoId = 할일_생성_및_ID_반환(accessToken);
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

        var firstTodoId = 할일_생성_및_ID_반환(accessToken);
        var secondTodoId = 할일_생성_및_ID_반환(accessToken);
        var todoIds = new HashMap<>();
        todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));
        그룹_할일_할당_요청(groupId, todoIds, accessToken);
        그룹_할일_할당_요청(groupId, todoIds, accessToken);

        // when
        var todos = 그룹_할일_목록_조회_요청(groupId, accessToken).jsonPath().getList("id");

        // then
        assertThat(todos.size()).isEqualTo(2);
    }

    @DisplayName("그룹 프로젝트를 생성 실패")
    @Nested
    class CreateGroupProjectFail {

        @DisplayName("그룹 ID가 없으면 404 상태 코드를 반환한다.")
        @Test
        void create_group_project_fail_by_no_group_id() {
            // when
            var response = 그룹_프로젝트_생성(0L, "project", accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("권한이 없는 사용자가 요청 시 403 상태 코드를 반환한다.")
        @Test
        void create_group_project_fail_by_no_permission() {
            // given
            var groupId = 그룹_생성및_ID_반환("group", accessToken);

            // when
            var response = 그룹_프로젝트_생성(groupId, "project", getFixtureSecondAccountAccessToken());

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("그룹 할일에 할당된 태그를 제거 실패")
    @Nested
    class UnassignTagFromGroupTodoFail {

        @DisplayName("잘못된 태그 ID로 요청 시 404 상태 코드를 반환한다.")
        @Test
        void unassign_tag_from_group_todo_fail_by_wrong_tag_id() {
            // given
            var groupId = 그룹_생성및_ID_반환("group", accessToken);
            var todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
            var tagId = 태그_생성_및_ID_반환(accessToken, "tag");
            그룹_할일_태그_할당_요청(groupId, todoId, tagId, accessToken);

            // when
            var response = 그룹_할일_태그_할당_해제_요청(groupId, todoId, 0L, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("잘못된 할일 ID로 요청 시 404 상태 코드를 반환한다.")
        @Test
        void unassign_tag_from_group_todo_fail_by_wrong_todo_id() {
            // given
            var groupId = 그룹_생성및_ID_반환("group", accessToken);
            var todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
            var tagId = 태그_생성_및_ID_반환(accessToken, "tag");
            그룹_할일_태그_할당_요청(groupId, todoId, tagId, accessToken);

            // when
            var response = 그룹_할일_태그_할당_해제_요청(groupId, 0L, tagId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("잘못된 그룹 ID로 요청 시 404 상태 코드를 반환한다.")
        @Test
        void unassign_tag_from_group_todo_fail_by_wrong_group_id() {
            // given
            var groupId = 그룹_생성및_ID_반환("group", accessToken);
            var todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
            var tagId = 태그_생성_및_ID_반환(accessToken, "tag");
            그룹_할일_태그_할당_요청(groupId, todoId, tagId, accessToken);

            // when
            var response = 그룹_할일_태그_할당_해제_요청(0L, todoId, tagId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("권한이 없는 사용자가 요청 시 403 상태 코드를 반환한다.")
        @Test
        void unassign_tag_from_group_todo_fail_by_no_permission() {
            // given
            var groupId = 그룹_생성및_ID_반환("group", accessToken);
            var todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
            var tagId = 태그_생성_및_ID_반환(accessToken, "tag");
            그룹_할일_태그_할당_요청(groupId, todoId, tagId, accessToken);

            // when
            var response = 그룹_할일_태그_할당_해제_요청(groupId, todoId, tagId, getFixtureSecondAccountAccessToken());

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }

        @DisplayName("할일이 그룹에 할당되지 않은 경우 400 상태 코드를 반환한다.")
        @Test
        void unassign_tag_from_group_todo_fail_by_todo_not_assigned() {
            // given
            var groupId = 그룹_생성및_ID_반환("group", accessToken);
            var todoId = 할일_생성_및_ID_반환(accessToken);
            var tagId = 태그_생성_및_ID_반환(accessToken, "tag");

            // when
            var response = 그룹_할일_태그_할당_해제_요청(groupId, todoId, tagId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }
    }

    @DisplayName("그룹 할일의 우선순위를 수정 실패")
    @Nested
    class UpdateGroupTodoPriorityFail {

        long groupId;
        long todoId;

        @BeforeEach
        void create_group_and_todo() {
            groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);
            todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        }

        @DisplayName("할 일 ID가 없으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_priority_without_todo_id() {
            // when
            var response = 그룹_할일_우선순위_수정_요청(groupId, 0L, "HIGH", accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_priority_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;

            // when
            var response = 그룹_할일_우선순위_수정_요청(notExistGroupId, todoId, "HIGH", accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("할 일이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_priority_with_not_exist_todo() {
            // given
            var notExistTodoId = 100L;

            // when
            var response = 그룹_할일_우선순위_수정_요청(groupId, notExistTodoId, "HIGH", accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
    }

    @DisplayName("그룹 할일의 상태를 수정 실패")
    @Nested
    class UpdateGroupTodoStatusFail {

        long groupId;
        long todoId;

        @BeforeEach
        void create_group_and_todo() {
            groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);
            todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        }

        @DisplayName("할 일 ID가 없으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_status_without_todo_id() {
            // when
            var response = 그룹_할일_상태_수정_요청(groupId, 0L, new HashMap<>(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_status_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;
            var body = new HashMap<>();
            body.put("status", "IN_PROGRESS");

            // when
            var response = 그룹_할일_상태_수정_요청(notExistGroupId, todoId, body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
    }

    @DisplayName("그룹에 사용자를 제거 실패")
    @Nested
    class RemoveUserFromGroupFail {

        long groupId;
        long accountId;

        @BeforeEach
        void create_group_and_account() {
            groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);
            accountId = 유저_가입_및_ID_반환("newUser1");
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void remove_user_from_group_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;
            var body = new HashMap<>();
            body.put("accountIds", List.of(accountId));

            // when
            var response = 그룹_사용자_제거_요청(notExistGroupId, body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("그룹에 포함하지 않는 사용자는 제거할 수 없다.")
        @Test
        void remove_user_from_group_with_not_group_member() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();
            var body = new HashMap<>();
            body.put("accountIds", List.of(accountId));

            // when
            var response = 그룹_사용자_제거_요청(groupId, body, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("그룹 할일을 수정 실패")
    @Nested
    class UpdateGroupTodoFail {

        long groupId;
        long todoId;

        @BeforeEach
        void create_group_and_todo() {
            groupId = 그룹_생성_요청_후_아이디_가져온다(accessToken);
            todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        }

        @DisplayName("할 일 ID가 없으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_without_todo_id() {
            // when
            var response = 그룹_할일_수정_요청(groupId, 0L, new HashMap<>(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;

            // when
            var response = 그룹_할일_수정_요청(notExistGroupId, todoId, new HashMap<>(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("할 일이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void update_group_todo_with_not_exist_todo() {
            // given
            var notExistTodoId = 100L;

            // when
            var response = 그룹_할일_수정_요청(groupId, notExistTodoId, new HashMap<>(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("할 일이 그룹에 할당되지 않으면 400 상태 코드를 반환한다.")
        @Test
        void update_group_todo_with_not_assigned_todo() {
            // given
            var otherTodoId = 할일_생성_및_ID_반환(accessToken);

            // when
            var response = 그룹_할일_수정_요청(groupId, otherTodoId, new HashMap<>(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹에 포함하지 않는 사용자는 업데이트 할 수 없다.")
        @Test
        void update_group_todo_with_not_group_member() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var response = 그룹_할일_수정_요청(groupId, todoId, new HashMap<>(), otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("그룹의 할 일에 할당된 멤버를 제거 실패")
    @Nested
    class UnassignTodoFromGroupMemberFail {

        long groupId;
        long memberId;
        long todoId;

        @BeforeEach
        void create_group_and_member_and_todo() {
            groupId = 그룹_생성및_ID_반환("group", accessToken);
            memberId = 유저_가입_및_ID_반환("newUser1");
            todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_사용자_추가_요청(groupId, List.of(memberId), accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
            그룹_할일을_멥버에게_할당_요청(groupId, todoId, List.of(memberId), accessToken);
        }

        @DisplayName("멤버 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void unassign_todo_from_group_member_without_member_id() {
            // when
            var response = 그룹_할일에_할당된_멤버_취소_요청(groupId, todoId, List.of(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("멤버 ID가 null이면 400 상태 코드를 반환한다.")
        @Test
        void unassign_todo_from_group_member_with_null_member_id() {
            // when
            var response = 그룹_할일에_할당된_멤버_취소_요청(groupId, todoId, null, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void unassign_todo_from_group_member_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;

            // when
            var response = 그룹_할일에_할당된_멤버_취소_요청(notExistGroupId, todoId, List.of(memberId), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("멤버가 아니면 403 상태 코드를 반환한다.")
        @Test
        void unassign_todo_from_group_member_with_not_member() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var response = 그룹_할일에_할당된_멤버_취소_요청(groupId, todoId, List.of(memberId), otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }

        @DisplayName("할 일이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void unassign_todo_from_group_member_with_not_exist_todo() {
            // given
            var notExistTodoId = 100L;

            // when
            var response = 그룹_할일에_할당된_멤버_취소_요청(groupId, notExistTodoId, List.of(memberId), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
    }

    @DisplayName("그룹의 할 일을 멤버에게 할당 실패")
    @Nested
    class AssignTodoToGroupMemberFail {

        long groupId;
        long memberId;
        long todoId;

        @BeforeEach
        void create_group_and_member_and_todo() {
            groupId = 그룹_생성및_ID_반환("group", accessToken);
            memberId = 유저_가입_및_ID_반환("newUser1");
            todoId = 할일_생성_및_ID_반환(accessToken);
            그룹_사용자_추가_요청(groupId, List.of(memberId), accessToken);
            그룹_할일_할당_요청(groupId, List.of(todoId), accessToken);
        }

        @DisplayName("멤버 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void assign_todo_to_group_member_without_member_id() {
            // when
            var response = 그룹_할일을_멥버에게_할당_요청(groupId, todoId, List.of(), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("멤버 ID가 null이면 400 상태 코드를 반환한다.")
        @Test
        void assign_todo_to_group_member_with_null_member_id() {
            // when
            var response = 그룹_할일을_멥버에게_할당_요청(groupId, todoId, null, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void assign_todo_to_group_member_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;

            // when
            var response = 그룹_할일을_멥버에게_할당_요청(notExistGroupId, todoId, List.of(memberId), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @DisplayName("맴버가 아니면 403 상태 코드를 반환한다.")
        @Test
        void assign_todo_to_group_member_with_not_member() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var response = 그룹_할일을_멥버에게_할당_요청(groupId, todoId, List.of(memberId), otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }

        @DisplayName("할 일이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void assign_todo_to_group_member_with_not_exist_todo() {
            // given
            var notExistTodoId = 100L;

            // when
            var response = 그룹_할일을_멥버에게_할당_요청(groupId, notExistTodoId, List.of(memberId), accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
    }

    @DisplayName("그룹에 사용자를 추가 실패")
    @Nested
    class AddUserToGroupFail {

        long groupId;

        @BeforeEach
        void create_group() {
            var body = new HashMap<>();
            body.put("name", "group");
            body.put("description", "description");
            var response = 그룹_생성_요청(body, accessToken);
            groupId = response.jsonPath().getLong("id");
        }

        @DisplayName("사용자 ID가 없으면 400 상태 코드를 반환한다.")
        @Test
        void add_user_to_group_without_user_id() {
            // given
            var body = new HashMap<>();
            body.put("accountIds", List.of());

            // when
            var response = 그룹_사용자_추가_요청(groupId, body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("사용자 ID가 null이면 400 상태 코드를 반환한다.")
        @Test
        void add_user_to_group_with_null_user_id() {
            // given
            var body = new HashMap<>();
            body.put("accountIds", null);

            // when
            var response = 그룹_사용자_추가_요청(groupId, body, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(400);
        }

        @DisplayName("그룹 소유주가 아닌 사용자가 사용자를 추가하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void add_user_to_group_with_other_user() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();
            var body = new HashMap<>();
            body.put("accountIds", List.of(1L, 2L, 3L));

            // when
            var response = 그룹_사용자_추가_요청(groupId, body, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }
    }

    @DisplayName("그룹 삭제 실패")
    @Nested
    class DeleteGroupFail {

        long groupId;

        @BeforeEach
        void create_group() {
            var body = new HashMap<>();
            body.put("name", "group");
            body.put("description", "description");
            var response = 그룹_생성_요청(body, accessToken);
            groupId = response.jsonPath().getLong("id");
        }

        @DisplayName("그룹 소유주가 아닌 사용자가 그룹을 삭제하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void delete_group_with_other_user() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();

            // when
            var response = 그룹_삭제_요청(groupId, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }

        @DisplayName("그룹이 존재하지 않으면 404 상태 코드를 반환한다.")
        @Test
        void delete_group_with_not_exist_group() {
            // given
            var notExistGroupId = 100L;

            // when
            var response = 그룹_삭제_요청(notExistGroupId, accessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(404);
        }
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
            var firstTodoId = 할일_생성_및_ID_반환(accessToken);
            var secondTodoId = 할일_생성_및_ID_반환(accessToken);
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
            var firstTodoId = 할일_생성_및_ID_반환(accessToken);
            var secondTodoId = 할일_생성_및_ID_반환(accessToken);
            var todoIds = new HashMap<>();
            todoIds.put("todoIds", List.of(firstTodoId, secondTodoId));

            // when
            var response = 그룹_할일_할당_요청(groupId, todoIds, otherAccessToken);

            // then
            assertThat(response.statusCode()).isEqualTo(403);
        }

        @DisplayName("그룹 소유주 혹은 멤버가 아닌 사용자가 할 일을 할당하려고 하면 403 상태 코드를 반환한다.")
        @Test
        void assign_todo_with_not_group_member() {
            // given
            var otherAccessToken = getFixtureSecondAccountAccessToken();
            var firstTodoId = 할일_생성_및_ID_반환(accessToken);
            var secondTodoId = 할일_생성_및_ID_반환(accessToken);
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
