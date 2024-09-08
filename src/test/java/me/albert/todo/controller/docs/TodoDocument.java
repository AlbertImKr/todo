package me.albert.todo.controller.docs;

import static me.albert.todo.TodoAcceptanceTest.prettyPrintRequest;
import static me.albert.todo.TodoAcceptanceTest.prettyPrintResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.Arrays;
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
                prettyPrintResponse()
        );
    }

    public static @NotNull RestDocumentationFilter assignTagToTodoDocumentation() {
        return document(
                "todos/assign-tag",
                prettyPrintRequest(),
                prettyPrintResponse(),
                requestFields(
                        fieldWithPath("tagId").description("태그 ID")
                                .attributes(key("constraints").value(ValidationMessages.TAG_ID_POSITIVE))
                )
        );
    }
}
