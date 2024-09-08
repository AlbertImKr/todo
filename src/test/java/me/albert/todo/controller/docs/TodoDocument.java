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
import me.albert.todo.domain.TodoPriority;
import me.albert.todo.domain.TodoStatus;
import me.albert.todo.utils.ValidationMessages;
import org.jetbrains.annotations.NotNull;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class TodoDocument {

    public static @NotNull RestDocumentationFilter createTodoDocumentation() {
        return document(
                "todos/create",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                requestFields(
                        fieldWithPath("title").description("할 일 제목")
                                .attributes(key("constraints").value(ValidationMessages.TODO_TITLE_MESSAGE)),
                        fieldWithPath("description").description("할 일 설명")
                                .attributes(key("constraints").value(ValidationMessages.TODO_DESCRIPTION_MESSAGE)),
                        fieldWithPath("dueDate").description("할 일 마감일")
                                .attributes(key("constraints").value(ValidationMessages.TODO_DUE_DATE_FUTURE))
                ),
                responseFields(
                        fieldWithPath("id").description("할 일 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateTodoDocumentation() {
        return document(
                "todos/update",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
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

    public static @NotNull RestDocumentationFilter deleteTodoDocumentation() {
        return document(
                "todos/delete",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter assignTagToTodoDocumentation() {
        return document(
                "todos/assign-tag",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("tagId").description("태그 ID")
                                .attributes(key("constraints").value(ValidationMessages.TAG_ID_POSITIVE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter unassignTagFromTodoDocumentation() {
        return document(
                "todos/unassign-tag",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("tagId").description("태그 ID"),
                        parameterWithName("todoId").description("할 일 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateTodoStatusDocumentation() {
        return document(
                "todos/update-status",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
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

    public static @NotNull RestDocumentationFilter updateTodoPriorityDocumentation() {
        return document(
                "todos/update-priority",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("priority").description("할 일 우선순위")
                                .attributes(key("constraints").value(Arrays.stream(TodoPriority.values())
                                                                             .map(Enum::name)
                                                                             .reduce((a, b) -> a + ", " + b)
                                                                             .orElse(""))
                                )
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateTodoNotificationDocumentation() {
        return document(
                "todos/update-notification",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("notifyAt").description("알림 설정")
                                .attributes(key("constraints").value(
                                        ValidationMessages.NOTIFICATIONS_SETTING_NOT_NUll_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter deleteTodoNotificationDocumentation() {
        return document(
                "todos/delete-notification",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                )
        );
    }
}
