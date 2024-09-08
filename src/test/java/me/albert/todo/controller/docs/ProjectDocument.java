package me.albert.todo.controller.docs;

import static me.albert.todo.TodoAcceptanceTest.prettyPrintRequest;
import static me.albert.todo.TodoAcceptanceTest.prettyPrintResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import jakarta.validation.constraints.NotNull;
import me.albert.todo.utils.ValidationMessages;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Attributes;

public class ProjectDocument {

    public static @NotNull RestDocumentationFilter createProjectDocumentation() {
        return document(
                "projects/create",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
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
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("projectId").description("프로젝트 ID")
                ),
                requestFields(
                        fieldWithPath("name").description("프로젝트 이름")
                                .attributes(
                                        Attributes.key("constraints").value(ValidationMessages.PROJECT_NAME_MESSAGE))
                )
        );
    }

    public static @NotNull RestDocumentationFilter deleteProjectDocumentation() {
        return document(
                "projects/delete",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("projectId").description("프로젝트 ID")
                )
        );
    }

    public static @NotNull RestDocumentationFilter listProjectDocumentation() {
        return document(
                "projects/list",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(),
                responseFields(
                        fieldWithPath("[].id").description("프로젝트 ID"),
                        fieldWithPath("[].name").description("프로젝트 이름")
                )
        );
    }

    public static @NotNull RestDocumentationFilter getProjectDocumentation() {
        return document(
                "projects/get",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("projectId").description("프로젝트 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("프로젝트 ID"),
                        fieldWithPath("name").description("프로젝트 이름"),
                        fieldWithPath("todos").description("프로젝트 할 일 목록"),
                        fieldWithPath("todos[].id").description("할 일 ID"),
                        fieldWithPath("todos[].title").description("할 일 제목"),
                        fieldWithPath("todos[].description").description("할 일 설명"),
                        fieldWithPath("todos[].dueDate").description("할 일 마감일"),
                        fieldWithPath("todos[].status").description("할 일 상태"),
                        fieldWithPath("todos[].createdAt").description("할 일 생성일"),
                        fieldWithPath("todos[].updatedAt").description("할 일 수정일"),
                        fieldWithPath("todos[].tags").description("할 일 태그 목록"),
                        fieldWithPath("todos[].priority").description("할 일 우선순위")
                )
        );
    }

    public static @NotNull RestDocumentationFilter assignTodoToProjectDocumentation() {
        return document(
                "projects/assign-todo",
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("projectId").description("프로젝트 ID")
                ),
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
                prettyPrintRequest(),
                prettyPrintResponse(),
                pathParameters(
                        parameterWithName("projectId").description("프로젝트 ID")
                ),
                requestFields(
                        fieldWithPath("todoIds").description("할 일 ID 목록")
                                .attributes(Attributes.key("constraints")
                                                    .value(ValidationMessages.PROJECT_ASSIGN_TODO_NOT_NULL))
                )
        );
    }
}
