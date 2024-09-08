package me.albert.todo.controller.docs;

import static me.albert.todo.TodoAcceptanceTest.prettyPrintRequest;
import static me.albert.todo.TodoAcceptanceTest.prettyPrintResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

import me.albert.todo.utils.ValidationMessages;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class RecurringTaskDocument {

    public static RestDocumentationFilter updateRecurringTaskDocumentation() {
        return document(
                "recurring-task/update",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                ),
                requestFields(
                        fieldWithPath("recurrencePattern").description("반복 주기")
                                .attributes(key("constraints").value(
                                        ValidationMessages.RECURRING_TASK_RECURRENCE_PATTERN_MESSAGE))
                )
        );
    }

    public static RestDocumentationFilter deleteRecurringTaskDocumentation() {
        return document(
                "recurring-task/delete",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("todoId").description("할 일 ID")
                )
        );
    }
}
