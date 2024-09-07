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

    /**
     * 프로젝트를 생성하고 ID 반환
     *
     * @param accessToken 액세스 토큰
     * @return 프로젝트 ID
     */
    public static Long 프로젝트_생성_및_ID_반환(String accessToken) {
        var body = new HashMap<>();
        body.put("name", "프로젝트");
        return 프로젝트_생성_요청(body, accessToken).jsonPath().getLong("id");
    }

    /**
     * 프로젝트 수정 요청
     *
     * @param body        프로젝트 수정 요청 바디
     * @param id          프로젝트 ID
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_수정_요청(
            HashMap<Object, Object> body, Long id, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .put("/projects/" + id)
                .then().log().all()
                .extract();
    }

    /**
     * 프로젝트 수정 요청
     *
     * @param body        프로젝트 수정 요청 바디
     * @param id          프로젝트 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_수정_요청(
            HashMap<Object, Object> body, Long id, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .put("/projects/" + id)
                .then().log().all()
                .extract();
    }


    /**
     * 프로젝트 삭제 요청
     *
     * @param id          프로젝트 ID
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_삭제_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/projects/" + id)
                .then().log().all()
                .extract();
    }

    /**
     * 프로젝트 삭제 요청
     *
     * @param id          프로젝트 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_삭제_요청(Long id, String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/projects/" + id)
                .then().log().all()
                .extract();
    }

    /**
     * 프로젝트 목록 조회 요청
     *
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_목록_조회_요청(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/projects")
                .then().log().all()
                .extract();
    }

    /**
     * 프로젝트 목록 조회 요청
     *
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_목록_조회_요청(String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/projects")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일을 프로젝트에 할당 요청
     *
     * @param id          프로젝트 ID
     * @param body        할 일 할당 요청 바디
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_할일_할당_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .put("/projects/" + id + "/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일을 프로젝트에 할당 요청
     *
     * @param id          프로젝트 ID
     * @param body        할 일 할당 요청 바디
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 프로젝트_할일_할당_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .put("/projects/" + id + "/todos")
                .then().log().all()
                .extract();
    }
}
