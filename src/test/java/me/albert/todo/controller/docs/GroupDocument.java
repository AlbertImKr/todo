package me.albert.todo.controller.docs;

import static me.albert.todo.TodoAcceptanceTest.prettyPrintRequest;
import static me.albert.todo.TodoAcceptanceTest.prettyPrintResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

import me.albert.todo.utils.ValidationMessages;
import org.jetbrains.annotations.NotNull;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class GroupDocument {

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
}
