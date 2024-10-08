package me.albert.todo.controller.docs;

import static me.albert.todo.TodoAcceptanceTest.prettyPrintRequest;
import static me.albert.todo.TodoAcceptanceTest.prettyPrintResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import me.albert.todo.utils.ValidationMessages;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Attributes;

public class TagDocument {

    public static RestDocumentationFilter createTagDocumentation() {
        return document(
                "tags/create",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                requestFields(
                        fieldWithPath("name").description("태그 이름").attributes(
                                Attributes.key("constraints").value(ValidationMessages.TAG_NAME_MESSAGE))
                ),
                responseFields(
                        fieldWithPath("id").description("생성된 태그의 ID")
                )
        );
    }

    public static RestDocumentationFilter searchTagDocumentation() {
        return document(
                "tags/search",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                queryParameters(
                        parameterWithName("name").description("태그 이름")
                ),
                responseFields(
                        fieldWithPath("id").description("태그 ID")
                )
        );
    }
}
