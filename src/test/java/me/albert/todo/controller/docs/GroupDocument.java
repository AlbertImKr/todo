package me.albert.todo.controller.docs;

import static me.albert.todo.TodoAcceptanceTest.prettyPrintRequest;
import static me.albert.todo.TodoAcceptanceTest.prettyPrintResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.Arrays;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.utils.ValidationMessages;
import org.jetbrains.annotations.NotNull;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class GroupDocument {

    public static @NotNull RestDocumentationFilter assignMembersToGroupTodoDocumentation() {
        return document(
                "groups/assign-members-todos",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("accountIds").description("할당할 사용자 ID 목록").attributes(
                                key("constraints").value(ValidationMessages.EMPTY_ACCOUNT_IDS))
                )
        );
    }

    public static @NotNull RestDocumentationFilter unassignMembersToGroupTodoDocumentation() {
        return document(
                "groups/unassign-members-todos",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("accountIds").description("할당 해제할 사용자 ID 목록").attributes(
                                key("constraints").value(ValidationMessages.EMPTY_ACCOUNT_IDS))
                )
        );
    }

    public static @NotNull RestDocumentationFilter assignTodosToGroupDocumentation() {
        return document(
                "groups/assign-todos",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                ),
                requestFields(
                        fieldWithPath("todoIds").description("할당할 할 일 ID 목록").attributes(
                                key("constraints").value(ValidationMessages.EMPTY_TODO_IDS))
                )
        );
    }

    public static @NotNull RestDocumentationFilter unassignTodosToGroupDocumentation() {
        return document(
                "groups/unassign-todos",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                ),
                requestFields(
                        fieldWithPath("todoIds").description("할당 해제할 할 일 ID 목록").attributes(
                                key("constraints").value(ValidationMessages.EMPTY_TODO_IDS))
                )
        );
    }

    public static @NotNull RestDocumentationFilter createGroupProjectDocumentation() {
        return document(
                "groups/create-project",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID")
                ),
                requestFields(
                        fieldWithPath("name").description("프로젝트 이름").attributes(
                                key("constraints").value(ValidationMessages.PROJECT_NAME_MESSAGE))
                ),
                responseFields(
                        fieldWithPath("id").description("프로젝트 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateGroupProjectDocumentation() {
        return document(
                "groups/update-project",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("projectId").description("프로젝트 ID")
                ),
                requestFields(
                        fieldWithPath("name").description("프로젝트 이름").attributes(
                                key("constraints").value(ValidationMessages.PROJECT_NAME_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter createGroupDocumentation() {
        return document(
                "groups/create",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                requestFields(
                        fieldWithPath("name").description("그룹 이름").attributes(
                                key("constraints").value(ValidationMessages.GROUP_NAME_MESSAGE)),
                        fieldWithPath("description").description("그룹 설명").attributes(
                                key("constraints").value(ValidationMessages.GROUP_DESCRIPTION_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter deleteGroupDocumentation() {
        return document(
                "groups/delete",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateGroupDocumentation() {
        return document(
                "groups/update",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                ),
                requestFields(
                        fieldWithPath("name").description("그룹 이름").attributes(
                                key("constraints").value(ValidationMessages.GROUP_NAME_MESSAGE)),
                        fieldWithPath("description").description("그룹 설명").attributes(
                                key("constraints").value(ValidationMessages.GROUP_DESCRIPTION_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter addUserToGroupDocumentation() {
        return document(
                "groups/add-user",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                ),
                requestFields(
                        fieldWithPath("accountIds").description("사용자 ID 목록").attributes(
                                key("constraints").value(ValidationMessages.EMPTY_ACCOUNT_IDS))
                )
        );
    }

    public static @NotNull RestDocumentationFilter removeUsersFromGroupDocumentation() {
        return document(
                "groups/remove-users",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                ),
                requestFields(
                        fieldWithPath("accountIds").description("사용자 ID 목록").attributes(
                                key("constraints").value(ValidationMessages.EMPTY_ACCOUNT_IDS))
                )
        );
    }

    public static @NotNull RestDocumentationFilter listGroupUsersDocumentation() {
        return document(
                "groups/list-users",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("id").description("그룹 ID")
                ),
                responseFields(
                        fieldWithPath("[].id").description("사용자 ID"),
                        fieldWithPath("[].username").description("사용자 이름"),
                        fieldWithPath("[].email").description("사용자 이메일")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateGroupTodoDocumentation() {
        return document(
                "groups/update-todo",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("title").description("할 일 제목")
                                .attributes(key("constraints").value(ValidationMessages.TODO_TITLE_MESSAGE)),
                        fieldWithPath("description").description("할 일 설명")
                                .attributes(key("constraints").value(ValidationMessages.TODO_DESCRIPTION_MESSAGE)),
                        fieldWithPath("dueDate").description("할 일 마감일")
                                .attributes(key("constraints").value(ValidationMessages.TODO_DUE_DATE_FUTURE)),
                        fieldWithPath("status").description("할 일 상태")
                                .attributes(key("constraints").value(Arrays.stream(TodoStatus.values())
                                                                             .map(Enum::name)
                                                                             .reduce((a, b) -> a + ", " + b)
                                                                             .orElse(""))
                                )
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateGroupTodoPriorityDocumentation() {
        return document(
                "groups/update-todo-priority",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("priority").description("할 일 우선순위")
                                .attributes(key("constraints").value(Arrays.stream(TodoStatus.values())
                                                                             .map(Enum::name)
                                                                             .reduce((a, b) -> a + ", " + b)
                                                                             .orElse("")))
                )
        );
    }

    public static @NotNull RestDocumentationFilter assignTagToGroupTodoDocumentation() {
        return document(
                "groups/assign-tag",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("tagId").description("태그 ID")
                                .attributes(key("constraints").value(ValidationMessages.TAG_ID_POSITIVE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter unassignTagFromGroupTodoDocumentation() {
        return document(
                "groups/unassign-tag",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID"),
                        parameterWithName("tagId").description("태그 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateGroupTodoStatusDocumentation() {
        return document(
                "groups/update-todo-status",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("groupId").description("그룹 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("status").description("할 일 상태")
                                .attributes(key("constraints").value(Arrays.stream(TodoStatus.values())
                                                                             .map(Enum::name)
                                                                             .reduce((a, b) -> a + ", " + b)
                                                                             .orElse(""))
                                )
                )
        );
    }
}
