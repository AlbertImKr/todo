package me.albert.todo.controller;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class RecurringTaskSteps {

    // 반복_작업_생성_요청
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
}
