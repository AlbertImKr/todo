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
     * 할 일 우선순위 변경 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(할 일 우선순위)
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_우선순위_변경_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/priority", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 우선순위 변경 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(할 일 우선순위)
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_우선순위_변경_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/priority", id)
                .then().log().all()
                .extract();
    }

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

    /**
     * 할 일 생성하고 해당 할 일 ID 반환
     *
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static Long 할일_생성_및_ID_반환(String accessToken) {
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
     * 할 일 목록 조회 요청
     *
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_목록_조회_요청(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 목록 조회 요청
     *
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_목록_조회_요청(String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 목록 조회 요청
     *
     * @param name        태그 이름
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_태그_이름으로_목록_조회_요청(
            String name, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .queryParam("name", name)
                .when()
                .get("/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 목록 조회 요청
     *
     * @param name        태그 이름
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_태그_이름으로_목록_조회_요청(
            String name, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .queryParam("tag", name)
                .when()
                .get("/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 프로젝트 ID로 목록 조회 요청
     *
     * @param projectId   프로젝트 ID
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_프로젝트_ID로_목록_조회_요청(
            Long projectId, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .queryParam("projectId", projectId)
                .when()
                .get("/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 프로젝트 ID로 목록 조회 요청
     *
     * @param projectId   프로젝트 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_프로젝트_ID로_목록_조회_요청(
            Long projectId, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .queryParam("projectId", projectId)
                .when()
                .get("/todos")
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 알림 설정 변경 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(알림 설정)
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_알림_설정_변경_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/notification-settings", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 알림 설정 변경 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(알림 설정)
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_알림_설정_변경_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/notification-settings", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 알림 설정 삭제 요청
     *
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_알림_설정_삭제_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}/notification-settings", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 알림 설정 삭제 요청
     *
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_알림_설정_삭제_요청(
            Long id, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}/notification-settings", id)
                .then().log().all()
                .extract();
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
                .put("/todos/{todoId}", id)
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
                .put("/todos/{todoId}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 태그 할당 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(태그 ID)
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_태그_할당_요청(Long id, HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/tags", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 태그 할당 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(태그 ID)
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_태그_할당_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/tags", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 태그 할당 해제 요청
     *
     * @param id          할 일 ID
     * @param tagId       태그 ID
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_태그_할당_해제_요청(Long id, Long tagId, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}/tags/{tagId}", id, tagId)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 태그 할당 해제 요청
     *
     * @param id          할 일 ID
     * @param tagId       태그 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_태그_할당_해제_요청(
            Long id, Long tagId, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}/tags/{tagId}", id, tagId)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 삭제 요청
     *
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_삭제_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 삭제 요청
     *
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_삭제_요청(Long id, String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/todos/{todoId}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 조회 요청
     *
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_조회_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/todos/{todoId}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 조회 요청
     *
     * @param id          할 일 ID
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_조회_요청(Long id, String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/todos/{todoId}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 상태 변경 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(할 일 상태)
     * @param accessToken 액세스 토큰
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_상태_변경_요청(Long id, HashMap<Object, Object> body, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/status", id)
                .then().log().all()
                .extract();
    }

    /**
     * 할 일 상태 변경 요청
     *
     * @param id          할 일 ID
     * @param body        요청 바디 맵(할 일 상태)
     * @param accessToken 액세스 토큰
     * @param spec        RestDocs 스펙
     * @return 응답 ExtractableResponse
     */
    public static ExtractableResponse<Response> 할일_상태_변경_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/todos/{todoId}/status", id)
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
                .put("/todos/{todoId}/users", id)
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
                .delete("/todos/{todoId}/users", id)
                .then().log().all()
                .extract();
    }
}
