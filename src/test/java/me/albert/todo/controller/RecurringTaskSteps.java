package me.albert.todo.controller;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class RecurringTaskSteps {

    public static ExtractableResponse<Response> 반복_작업_생성_요청(
            HashMap<Object, Object> body, long todoId, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/todos/" + todoId + "/recurring-tasks")
                .then().log().all()
                .extract();
    }


    /**
     * 반복 작업 생성 후 아이디를 가져온다.
     *
     * @param todoId      할일 아이디
     * @param accessToken 엑세스 토큰
     * @return 반복 작업 아이디
     */
    public static Long 반복_작업_아이디(long todoId, String accessToken) {
        var body = new HashMap<>();
        body.put("recurrencePattern", "P1D");

        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/todos/" + todoId + "/recurring-tasks")
                .then().log().all()
                .extract().jsonPath().getLong("id");
    }

    public static ExtractableResponse<Response> 반복_작업_수정_요청(
            HashMap<Object, Object> body, long recurringTaskId, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/recurring-tasks/" + recurringTaskId)
                .then().log().all()
                .extract();
    }
}
