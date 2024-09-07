package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class TodoSteps {

    /**
     * 할 일 생성 요청
     *
     * @param body        요청 바디 맵(할 일 제목, 설명, 마감일)
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_생성_요청(
            HashMap<Object, Object> body, String accessToken,
            RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/todos")
                .then().log().all()
                .extract();
    }

    public static Long 할일_이이디_생성_요청(String accessToken) {
        var dueDate = LocalDateTime.now().plusDays(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        );
        var body = new HashMap<>();
        body.put("title", "할 일 제목");
        body.put("description", "할 일 설명");
        body.put("dueDate", dueDate);
        return 할일_생성_요청(body, accessToken, new RequestSpecBuilder().build()).jsonPath().getLong("id");
    }

    /**
     * 할 일 수정 요청
     *
     * @param body        요청 바디 맵(할 일 제목, 설명, 마감일, 상태)
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_수정_요청(HashMap<Object, Object> body, Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/" + id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 수정 요청
     *
     * @param body        요청 바디 맵(할 일 제목, 설명, 마감일, 상태)
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_수정_요청(
            HashMap<Object, Object> body, Long id, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 할일_삭제_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 할일_조회_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/todos/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 할일_상태_변경_요청(Long id, HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/" + id + "/status")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 할일_사용자_할당_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/" + id + "/users")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 할일_사용자_할당_해제_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/todos/" + id + "/users")
                .then().log().all()
                .extract();
    }
}
