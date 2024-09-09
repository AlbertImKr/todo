package me.albert.todo.controller.steps;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;

public class AccountSteps {

    public static String FIXTURE_FIRST_ACCOUNT_USERNAME = "newUser";
    public static String FIXTURE_SECOND_ACCOUNT_USERNAME = "otherNewUser";

    /**
     * 화원 가입 요청
     *
     * @param body 요청 바디
     * @return ExtractableResponse
     */
    public static ExtractableResponse<Response> 화원_가입_요청(HashMap<Object, Object> body) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then().log().all()
                .extract();
    }

    /**
     * 화원 가입 요청
     *
     * @param body 요청 바디
     * @param spec RequestSpecification
     * @return ExtractableResponse
     */
    public static ExtractableResponse<Response> 화원_가입_요청(HashMap<Object, Object> body, RequestSpecification spec) {
        return given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then().log().all()
                .extract();
    }

    /**
     * 로그인 요청
     *
     * @param body 요청 바디
     * @return ExtractableResponse
     */
    public static ExtractableResponse<Response> 로그인_요청(HashMap<Object, Object> body) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users/login")
                .then().log().all()
                .extract();
    }

    /**
     * 로그인 요청
     *
     * @param body 요청 바디
     * @param spec RequestSpecification
     * @return ExtractableResponse
     */
    public static ExtractableResponse<Response> 로그인_요청(HashMap<Object, Object> body, RequestSpecification spec) {
        return given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users/login")
                .then().log().all()
                .extract();
    }

    /**
     * 유저를 가입시키고 ID를 반환한다.
     *
     * @param username
     * @return ID
     */
    public static Long 유저_가입_및_ID_반환(String username) {
        var registerBody = new HashMap<>();
        registerBody.put("username", username);
        registerBody.put("password", "Password1!");
        registerBody.put("confirmPassword", "Password1!");
        return 화원_가입_요청(registerBody).jsonPath().getLong("id");
    }

    /**
     * newUser의 엑세스 토큰을 가져온다.
     *
     * @return 엑세스 토큰
     */
    public static String getFixtureFirstAccountAccessToken() {
        var registerBody = new HashMap<>();
        registerBody.put("username", FIXTURE_FIRST_ACCOUNT_USERNAME);
        registerBody.put("password", "Password1!");
        registerBody.put("confirmPassword", "Password1!");
        화원_가입_요청(registerBody);

        var loginBody = new HashMap<>();
        loginBody.put("username", "newUser");
        loginBody.put("password", "Password1!");
        var response = 로그인_요청(loginBody);

        return response.jsonPath().getString("accessToken");
    }

    /**
     * otherNewUser의 엑세스 토큰을 가져온다.
     *
     * @return 엑세스 토큰
     */
    public static String getFixtureSecondAccountAccessToken() {
        var registerBody = new HashMap<>();
        registerBody.put("username", FIXTURE_SECOND_ACCOUNT_USERNAME);
        registerBody.put("password", "Password1!");
        registerBody.put("confirmPassword", "Password1!");
        화원_가입_요청(registerBody);

        var loginBody = new HashMap<>();
        loginBody.put("username", "otherNewUser");
        loginBody.put("password", "Password1!");
        var response = 로그인_요청(loginBody);

        return response.jsonPath().getString("accessToken");
    }
}
