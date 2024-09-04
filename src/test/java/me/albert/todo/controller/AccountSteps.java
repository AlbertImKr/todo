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

    public static String getAccessToken() {
        var registerBody = new HashMap<>();
        registerBody.put("username", "newUser");
        registerBody.put("password", "Password1!");
        registerBody.put("confirmPassword", "Password1!");
        화원_가입_요청(registerBody);

        var loginBody = new HashMap<>();
        loginBody.put("username", "newUser");
        loginBody.put("password", "Password1!");
        var response = 로그인_요청(loginBody);

        return response.jsonPath().getString("accessToken");
    }
}
