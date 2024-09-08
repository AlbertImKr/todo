package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;

public class TagSteps {

    /**
     * 태그 생성 요청
     *
     * @param body        요청 바디
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 태그_생성_요청(HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/tags")
                .then().log().all()
                .extract();
    }

    /**
     * 태그 생성 요청
     *
     * @param body        요청 바디
     * @param accessToken 엑세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 태그_생성_요청(
            HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/tags")
                .then().log().all()
                .extract();
    }

    /**
     * 태그 이름으로 태그 조회 요청
     *
     * @param query       쿼리
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 태그_이름으로_태그_조회_요청(HashMap<String, Object> query, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .queryParams(query)
                .when()
                .get("/tags")
                .then().log().all()
                .extract();
    }

    /**
     * 태그 이름으로 태그 조회 요청
     *
     * @param query       쿼리
     * @param accessToken 엑세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 태그_이름으로_태그_조회_요청(
            HashMap<String, Object> query, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .queryParams(query)
                .when()
                .get("/tags")
                .then().log().all()
                .extract();
    }

    /**
     * 태그 ID로 태그 조회 요청
     *
     * @param accessToken 엑세스 토큰
     * @return 태그 ID
     */
    public static long 태그_생성_및_ID_반환(String accessToken, String name) {
        var body = new HashMap<>();
        body.put("name", name);
        return 태그_생성_요청(body, accessToken).jsonPath().getLong("id");
    }
}
