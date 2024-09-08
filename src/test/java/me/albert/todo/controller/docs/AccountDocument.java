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

import me.albert.todo.utils.ValidationMessages;
import org.jetbrains.annotations.NotNull;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class AccountDocument {

    public static @NotNull RestDocumentationFilter registerAccountDocumentation() {
        return document(
                "account/register",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                requestFields(
                        fieldWithPath("username").description("유저 이름").attributes(
                                key("constraints").value(ValidationMessages.ACCOUNT_USERNAME_MESSAGE)),
                        fieldWithPath("password").description("비밀번호").attributes(
                                key("constraints").value(ValidationMessages.ACCOUNT_PASSWORD_MESSAGE)),
                        fieldWithPath("confirmPassword").description("비밀번호 확인").attributes(
                                key("constraints").value(ValidationMessages.ACCOUNT_CONFIRM_PASSWORD_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter loginAccountDocumentation() {
        return document(
                "account/login",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                requestFields(
                        fieldWithPath("username").description("유저 이름").attributes(
                                key("constraints").value(ValidationMessages.ACCOUNT_USERNAME_MESSAGE))
                        ,
                        fieldWithPath("password").description("비밀번호").attributes(
                                key("constraints").value(ValidationMessages.ACCOUNT_PASSWORD_MESSAGE)
                        )
                ),
                responseFields(
                        fieldWithPath("accessToken").description("액세스 토큰"),
                        fieldWithPath("refreshToken").description("리프레시 토큰")
                )
        );
    }
}

