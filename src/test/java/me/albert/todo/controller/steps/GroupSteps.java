package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;

public class GroupSteps {

    /**
     * 그룹에 사용자 추가 요청
     *
     * @param id          그룹 ID
     * @param body        사용자 할당 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     */
    public static ExtractableResponse<Response> 그룹_사용자_추가_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 추가 요청
     *
     * @param id          그룹 ID
     * @param accountIds  사용자 할당 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     */
    public static ExtractableResponse<Response> 그룹_사용자_추가_요청(
            Long id, List<Long> accountIds, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("accountIds", accountIds);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 추가 요청
     *
     * @param id          그룹 ID
     * @param body        사용자 할당 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     */
    public static ExtractableResponse<Response> 그룹_사용자_추가_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 제거 요청
     *
     * @param id          그룹 ID
     * @param body        사용자 제거 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     */
    public static ExtractableResponse<Response> 그룹_사용자_제거_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 제거 요청
     *
     * @param id          그룹 ID
     * @param body        사용자 제거 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     */
    public static ExtractableResponse<Response> 그룹_사용자_제거_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 목록 조회 요청
     *
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     */
    public static ExtractableResponse<Response> 그룹_사용자_목록_조회_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 목록 조회 요청
     *
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     */
    public static ExtractableResponse<Response> 그룹_사용자_목록_조회_요청(
            Long id, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 삭제 요청
     *
     * @param id          그룹 ID
     * @param body        사용자 삭제 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     */
    public static ExtractableResponse<Response> 그룹_사용자_삭제_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹에 사용자 삭제 요청
     *
     * @param id          그룹 ID
     * @param body        사용자 삭제 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     */
    public static ExtractableResponse<Response> 그룹_사용자_삭제_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{id}/users", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 생성 요청
     *
     * @param name        그룹 이름
     * @param accessToken 액세스 토큰
     * @return 그룹 ID
     */
    public static Long 그룹_생성및_ID_반환(String name, String accessToken) {
        var body = new HashMap<>();
        body.put("name", name);
        body.put("description", "description");
        return 그룹_생성_요청(body, accessToken).jsonPath().getLong("id");
    }

    /**
     * 그룹 생성 요청
     *
     * @param body        그룹 생성 요청 바디 (name, description)
     * @param accessToken 액세스 토큰
     * @return 응답
     */
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

    /**
     * 그룹 생성 요청
     *
     * @param body        그룹 수정 요청 바디 (name, description)
     * @param accessToken 액세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_생성_요청(
            HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/groups")
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 생성
     *
     * @param groupId     그룹 ID
     * @param name        프로젝트 이름
     * @param accessToken 액세스 토큰
     * @return 프로젝트 ID
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_생성(long groupId, String name, String accessToken) {
        var body = new HashMap<>();
        body.put("name", name);

        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/groups/{groupId}/projects", groupId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 생성
     *
     * @param groupId     그룹 ID
     * @param name        프로젝트 이름
     * @param accessToken 액세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 프로젝트 ID
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_생성(
            long groupId, String name, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("name", name);

        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/groups/{groupId}/projects", groupId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 수정
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param name        프로젝트 이름
     * @param accessToken 액세스 토큰
     * @return 프로젝트 ID
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_수정(
            long groupId, long projectId, String name, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("name", name);

        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/projects/{projectId}", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 수정
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param name        프로젝트 이름
     * @param accessToken 액세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 프로젝트 ID
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_수정(
            long groupId, long projectId, String name, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("name", name);

        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/projects/{projectId}", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 삭제
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param accessToken 액세스 토큰
     * @return 프로젝트 ID
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_삭제(
            long groupId, long projectId, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/groups/{groupId}/projects/{projectId}", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 삭제
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param accessToken 액세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 프로젝트 ID
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_삭제(
            long groupId, long projectId, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/groups/{groupId}/projects/{projectId}", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 할일 할당 요청
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param todoIds     할당할 할일 ID 목록
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_할일_할당_요청(
            Long groupId, Long projectId, List<Long> todoIds, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("todoIds", todoIds);
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/projects/{projectId}/todos", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 할일 할당 요청
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param todoIds     할당할 할일 ID 목록
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_할일_할당_요청(
            Long groupId, Long projectId, List<Long> todoIds, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("todoIds", todoIds);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/projects/{projectId}/todos", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 할일 할당 해제 요청
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param todoIds     할당 해제할 할일 ID 목록
     * @param accessToken 액세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_할일_할당_해제_요청(
            Long groupId, Long projectId, List<Long> todoIds, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("todoIds", todoIds);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{groupId}/projects/{projectId}/todos", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트 할일 할당 해제 요청
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param todoIds     할당 해제할 할일 ID 목록
     * @param accessToken 액세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_프로젝트_할일_할당_해제_요청(
            Long groupId, Long projectId, List<Long> todoIds, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("todoIds", todoIds);
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{groupId}/projects/{projectId}/todos", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 삭제 요청
     *
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_삭제_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/groups/{id}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 삭제 요청
     *
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_삭제_요청(Long id, String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/groups/{id}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 수정 요청
     *
     * @param body        그룹 수정 요청 바디 (name, description)
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_수정_요청(
            HashMap<Object, Object> body, Long id, String
            accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 수정 요청
     *
     * @param body        그룹 수정 요청 바디 (name, description)
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_수정_요청(
            HashMap<Object, Object> body, Long id, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 목록 조회 요청
     *
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_목록_조회_요청(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups")
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 목록 조회 요청
     *
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_목록_조회_요청(String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups")
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 할당 요청
     *
     * @param id          그룹 ID
     * @param body        할당할 할일 ID 목록 요청 바디 (todoIds)
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_할당_요청(
            Long id, HashMap<Object, Object> body, String
            accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 그룹_할일_할당_요청(
            Long id, List<Long> todoIds, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("todoIds", todoIds);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 할당 요청
     *
     * @param id          그룹 ID
     * @param body        할당할 할일 ID 목록 요청 바디 (todoIds)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_할당_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 할당 해제 요청
     *
     * @param id          그룹 ID
     * @param body        할당 해제할 할일 ID 목록 요청 바디 (todoIds)
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_할당_해제_요청(
            Long id, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 할당 해제 요청
     *
     * @param id          그룹 ID
     * @param body        할당 해제할 할일 ID 목록 요청 바디 (todoIds)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_할당_해제_요청(
            Long id, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일을 멥버에게 할당 요청
     *
     * @param group       그룹 ID
     * @param todoId      할당할 할일 ID
     * @param body        할당 요청 바디 (accountIds)
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일을_멥버에게_할당_요청(
            Long group, Long todoId, List<Long> accountIds, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("accountIds", accountIds);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/users", group, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일을 멥버에게 할당 요청
     *
     * @param group       그룹 ID
     * @param todoId      할당할 할일 ID
     * @param accountIds  할당될 멥버 ID 목록
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일을_멥버에게_할당_요청(
            Long group, Long todoId, List<Long> accountIds, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("accountIds", accountIds);
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/users", group, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일에 할당된 멥버 취소 요청
     *
     * @param group       그룹 ID
     * @param todoId      할당된 할일 ID
     * @param accountIds  취소할 멥버 ID 목록
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일에_할당된_멤버_취소_요청(
            Long group, Long todoId, List<Long> accountIds, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("accountIds", accountIds);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{groupId}/todos/{todoId}/users", group, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일에 할당된 멥버 취소 요청
     *
     * @param group       그룹 ID
     * @param todoId      할당된 할일 ID
     * @param accountIds  취소할 멥버 ID 목록
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일에_할당된_멤버_취소_요청(
            Long group, Long todoId, List<Long> accountIds, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("accountIds", accountIds);
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .delete("/groups/{groupId}/todos/{todoId}/users", group, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 수정 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param body        할일 수정 요청 바디 (name, description)
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_수정_요청(
            Long groupId, Long todoId, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 수정 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param body        할일 수정 요청 바디 (name, description)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_수정_요청(
            Long groupId, Long todoId, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 우선순위 수정 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param priority    할일 우선순위
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_우선순위_수정_요청(
            Long groupId, Long todoId, String priority, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("priority", priority);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/priority", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 우선순위 수정 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param priority    할일 우선순위
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_우선순위_수정_요청(
            Long groupId, Long todoId, String priority, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("priority", priority);
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/priority", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 태그 할당 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param tagId       태그 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_태그_할당_요청(
            Long groupId, Long todoId, Long tagId, String accessToken
    ) {
        var body = new HashMap<>();
        body.put("tagId", tagId);
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/tags", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 태그 할당 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param tagId       태그 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_태그_할당_요청(
            Long groupId, Long todoId, Long tagId, String accessToken, RequestSpecification spec
    ) {
        var body = new HashMap<>();
        body.put("tagId", tagId);
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/tags", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 태그 할당 해제 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param tagId       태그 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_태그_할당_해제_요청(
            Long groupId, Long todoId, Long tagId, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .when()
                .delete("/groups/{groupId}/todos/{todoId}/tags/{tagId}", groupId, todoId, tagId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 태그 할당 해제 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param tagId       태그 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_태그_할당_해제_요청(
            Long groupId, Long todoId, Long tagId, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .when()
                .delete("/groups/{groupId}/todos/{todoId}/tags/{tagId}", groupId, todoId, tagId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 상태 수정 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param body        할일 상태 수정 요청 바디 (status)
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_상태_수정_요청(
            Long groupId, Long todoId, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/status", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 상태 수정 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param body        할일 상태 수정 요청 바디 (status)
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_상태_수정_요청(
            Long groupId, Long todoId, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/status", groupId, todoId)
                .then().log().all()
                .extract();
    }

    public static long 그룹_생성_요청_후_아이디_가져온다(String accessToken) {
        var body = new HashMap<>();
        body.put("name", "그룹 이름");
        body.put("description", "그룹 설명");
        return 그룹_생성_요청(body, accessToken).jsonPath().getLong("id");
    }

    /**
     * 그룹 할일 목록 조회 요청
     *
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_목록_조회_요청(Long id, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 목록 조회 요청
     *
     * @param id          그룹 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_목록_조회_요청(Long id, String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{id}/todos", id)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 조회 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_조회_요청(Long groupId, Long todoId, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{groupId}/todos/{todoId}", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 조회 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_조회_요청(Long groupId, Long todoId, String accessToken, RequestSpecification spec) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{groupId}/todos/{todoId}", groupId, todoId)
                .then().log().all()
                .extract();
    }


    /**
     * 그룹 프로젝트별 할일 목록 조회 요청
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_프로젝트별_할일_목록_조회_요청(Long groupId, Long projectId, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{groupId}/projects/{projectId}/todos", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 프로젝트별 할일 목록 조회 요청
     *
     * @param groupId     그룹 ID
     * @param projectId   프로젝트 ID
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_프로젝트별_할일_목록_조회_요청(
            Long groupId, Long projectId, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/groups/{groupId}/projects/{projectId}/todos", groupId, projectId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 반복 설정 추가 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param body        반복 설정 추가 요청 바디
     * @param accessToken 엑세스 토큰
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_반복_설정_추가_요청(
            Long groupId, Long todoId, HashMap<Object, Object> body, String accessToken
    ) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .post("/groups/{groupId}/todos/{todoId}/recurring-tasks", groupId, todoId)
                .then().log().all()
                .extract();
    }

    /**
     * 그룹 할일 반복 설정 추가 요청
     *
     * @param groupId     그룹 ID
     * @param todoId      할일 ID
     * @param body        반복 설정 추가 요청 바디
     * @param accessToken 엑세스 토큰
     * @param spec        docs 생성하기 위한 RequestSpecification
     * @return 응답
     */
    public static ExtractableResponse<Response> 그룹_할일_반복_설정_추가_요청(
            Long groupId, Long todoId, HashMap<Object, Object> body, String accessToken, RequestSpecification spec
    ) {
        return given(spec).log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .contentType("application/json")
                .when()
                .put("/groups/{groupId}/todos/{todoId}/recurring-tasks", groupId, todoId)
                .then().log().all()
                .extract();
    }
}
