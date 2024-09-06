package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class GroupSteps {

    public static ExtractableResponse<Response> 그룹_생성_요청(HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/groups")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 그룹_수정_요청(HashMap<Object, Object> body, Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 그룹_목록_조회_요청(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 그룹_할일_할당_요청(Long id, HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/" + id + "/todos")
                .then().log().all()
                .extract();
    }
}
