package me.albert.todo.controller;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;

public class AccountSteps {

    public static ExtractableResponse<Response> 화원_가입_요청(HashMap<Object, Object> body) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인_요청(HashMap<Object, Object> body) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users/login")
                .then().log().all()
                .extract();
    }
}
