package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;

public class ProjectSteps {

    /**
     * 프로젝트 생성 요청
     *
     * @param body        프로젝트 생성 요청 바디
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_생성_요청(HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/projects")
                .then().log().all()
                .extract();
    }

    /**
     * 프로젝트 생성 요청
     *
     * @param body        프로젝트 생성 요청 바디
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_생성_요청(
            HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/projects")
                .then().log().all()
                .extract();
    }
}
