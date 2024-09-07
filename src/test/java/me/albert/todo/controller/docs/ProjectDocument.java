package me.albert.todo.controller.docs;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import jakarta.validation.constraints.NotNull;
import me.albert.todo.utils.ValidationMessages;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Attributes;

public class ProjectDocument {

    public static @NotNull RestDocumentationFilter createProjectDocumentation() {
        return document(
                "projects/create",
                requestFields(
                        fieldWithPath("name").description("프로젝트 이름")
                                .attributes(
                                        Attributes.key("constraints").value(ValidationMessages.PROJECT_NAME_MESSAGE))
                ),
                responseFields(
                        fieldWithPath("id").description("프로젝트 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter updateProjectDocumentation() {
        return document(
                "projects/update",
                requestFields(
                        fieldWithPath("name").description("프로젝트 이름")
                                .attributes(
                                        Attributes.key("constraints").value(ValidationMessages.PROJECT_NAME_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter deleteProjectDocumentation() {
        return document(
                "projects/delete"
        );
    }

    public static @NotNull RestDocumentationFilter listProjectDocumentation() {
        return document(
                "projects/list",
                responseFields(
                        fieldWithPath("[].id").description("프로젝트 ID"),
                        fieldWithPath("[].name").description("프로젝트 이름")
                )
        );
    }

    public static @NotNull RestDocumentationFilter assignTodoToProjectDocumentation() {
        return document(
                "projects/assign-todo",
                requestFields(
                        fieldWithPath("todoIds").description("할 일 ID 목록")
                                .attributes(Attributes.key("constraints")
                                                    .value(ValidationMessages.PROJECT_ASSIGN_TODO_NOT_NULL))
                )
        );
    }

    public static @NotNull RestDocumentationFilter unassignTodoFromProjectDocumentation() {
        return document(
                "projects/unassign-todo",
                requestFields(
                        fieldWithPath("todoIds").description("할 일 ID 목록")
                                .attributes(Attributes.key("constraints")
                                                    .value(ValidationMessages.PROJECT_ASSIGN_TODO_NOT_NULL))
                )
        );
    }
}
