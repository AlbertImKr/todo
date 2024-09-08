package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;

public class RecurringTaskSteps {

    /**
     * 반복 작업 업데이트 요청
     *
     * @param body        요청 바디
     * @param todoId      할일 아이디
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 반복_작업_업데이트_요청(
            HashMap<Object, Object> body, long todoId, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/recurring-tasks", todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 반복 작업 업데이트 요청
     *
     * @param body        요청 바디
     * @param todoId      할일 아이디
     * @param accessToken 엑세스 토큰
     * @param spec        스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 반복_작업_업데이트_요청(
            HashMap<Object, Object> body, long todoId, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/recurring-tasks", todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 반복 작업 삭제 요청
     *
     * @param todoId      할일 아이디
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 반복_작업_삭제_요청(
            long todoId,
            String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}/recurring-tasks/", todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 반복 작업 삭제 요청
     *
     * @param todoId      할일 아이디
     * @param accessToken 엑세스 토큰
     * @param spec        스펙
     * @return 응답
     */
    public static ExtractableResponse<Response> 반복_작업_삭제_요청(
            long todoId,
            String accessToken,
            RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}/recurring-tasks/", todoId)
                .then().log().all()
                .extract();
    }

}
