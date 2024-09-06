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

    public static ExtractableResponse<Response> 그룹_할일_할당_해제_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/" + id + "/todos")
                .then().log().all()
                .extract();
    }

    public static long 그룹_생성_요청_후_아이디_가져온다(String accessToken) {
        var body = new HashMap<>();
        body.put("name", "그룹 이름");
        body.put("description", "그룹 설명");
        return 그룹_생성_요청(body, accessToken).jsonPath().getLong("id");
    }

    public static ExtractableResponse<Response> 그룹_할일_목록_조회_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/" + id + "/todos")
                .then().log().all()
                .extract();
    }
}
