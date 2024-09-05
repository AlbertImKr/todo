package me.albert.todo.controller;

import static me.albert.todo.controller.steps.AccountSteps.로그인_요청;
import static me.albert.todo.controller.steps.AccountSteps.화원_가입_요청;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import me.albert.todo.TodoAcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Account 인수 테스트")
class AccountControllerTest extends TodoAcceptanceTest {

    @DisplayName("회원가입 성공 시 201 상태 코드를 반환한다")
    @Test
    void registerReturnsCreatedStatus() {
        // given
        var body = new HashMap<>();
        body.put("username", "newUser");
        body.put("password", "Password1!");
        body.put("confirmPassword", "Password1!");

        // when
        var target = 화원_가입_요청(body);

        // then
        assertThat(target.statusCode()).isEqualTo(201);

    }

    @DisplayName("로그인 성공 시 토큰을 반환한다")
    @Test
    void login_returns_tokens() {
        // given
        var registerBody = new HashMap<>();
        registerBody.put("username", "newUser");
        registerBody.put("password", "Password1!");
        registerBody.put("confirmPassword", "Password1!");
        화원_가입_요청(registerBody);

        var loginBody = new HashMap<>();
        loginBody.put("username", "newUser");
        loginBody.put("password", "Password1!");

        // when
        var target = 로그인_요청(loginBody);

        // then
        assertThat(target.statusCode()).isEqualTo(200);
        assertThat(target.body().jsonPath().getString("accessToken")).isNotBlank();
        assertThat(target.body().jsonPath().getString("refreshToken")).isNotBlank();
    }

    @Nested
    @DisplayName("회원가입 실패 테스트")
    class RegisterFailureByPassword {

        @DisplayName("비밀번호와 비밀번호 확인이 일치하지 않으면 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_passwords_do_not_match() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Password1!");
            body.put("confirmPassword", "Password2!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo("비밀번호가 일치하지 않습니다.")
            );
        }

        @DisplayName("이미 존재하는 유저 이름으로 회원가입 시 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_username_is_existed() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Password1!");
            body.put("confirmPassword", "Password1!");

            // when
            화원_가입_요청(body);
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo("이미 존재하는 유저 이름입니다.")
            );
        }

        @DisplayName("비밀번호가 8자 이하인 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_password_is_less_than_8_characters() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Pass1!");
            body.put("confirmPassword", "Pass1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("비밀번호에 숫자가 없는 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_password_does_not_contain_number() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Password!");
            body.put("confirmPassword", "Password!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("비밀번호에 대문자가 없는 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_password_does_not_contain_uppercase() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "password1!");
            body.put("confirmPassword", "password1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("비밀번호에 특수문자가 없는 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_password_does_not_contain_special_character() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Password1");
            body.put("confirmPassword", "Password1");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("비밀번호에 공백이 있는 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_password_contains_whitespace() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Password 1!");
            body.put("confirmPassword", "Password 1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("유저 이름에 공백이 있는 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_username_contains_whitespace() {
            // given
            var body = new HashMap<>();
            body.put("username", "new User");
            body.put("password", "Password1!");
            body.put("confirmPassword", "Password1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름은 영문과 숫자만 포함한 5자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("유저 이름이 5자 미만인 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_username_is_less_than_5_characters() {
            // given
            var body = new HashMap<>();
            body.put("username", "user");
            body.put("password", "Password1!");
            body.put("confirmPassword", "Password1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름은 영문과 숫자만 포함한 5자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("유저 이름이 20자 초과인 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_username_is_more_than_20_characters() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUsernewUsernewUsernewUsernewUser");
            body.put("password", "Password1!");
            body.put("confirmPassword", "Password1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름은 영문과 숫자만 포함한 5자 이상 20자 이하로 입력해주세요.")
            );
        }

        @DisplayName("유저 이름에 특수문자가 있는 경우 400 상태 코드를 반환한다")
        @Test
        void register_returns_bad_request_when_username_contains_special_character() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser!");
            body.put("password", "Password1!");
            body.put("confirmPassword", "Password1!");

            // when
            var target = 화원_가입_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(400),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름은 영문과 숫자만 포함한 5자 이상 20자 이하로 입력해주세요.")
            );
        }
    }

    @Nested
    @DisplayName("로그인 실패 테스트")
    class LoginFailure {

        @DisplayName("유저 이름이 존재하지 않으면 401 상태 코드를 반환한다")
        @Test
        void login_returns_unauthorized_when_username_not_exist() {
            // given
            var body = new HashMap<>();
            body.put("username", "newUser");
            body.put("password", "Password1!");

            // when
            var target = 로그인_요청(body);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(404),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름 또는 비밀번호가 일치하지 않습니다.")
            );
        }

        @DisplayName("비밀번호가 일치하지 않으면 401 상태 코드를 반환한다")
        @Test
        void login_returns_unauthorized_when_password_not_matched() {
            // given
            var registerBody = new HashMap<>();
            registerBody.put("username", "newUser");
            registerBody.put("password", "Password1!");
            registerBody.put("confirmPassword", "Password1!");
            화원_가입_요청(registerBody);

            var loginBody = new HashMap<>();
            loginBody.put("username", "newUser");
            loginBody.put("password", "Password2!");

            // when
            var target = 로그인_요청(loginBody);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(401),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름 또는 비밀번호가 일치하지 않습니다.")
            );
        }

        @DisplayName("비밀번호가 공백인 경우 401 상태 코드를 반환한다")
        @Test
        void login_returns_unauthorized_when_password_is_blank() {
            // given
            var registerBody = new HashMap<>();
            registerBody.put("username", "newUser");
            registerBody.put("password", "Password1!");
            registerBody.put("confirmPassword", "Password1!");
            화원_가입_요청(registerBody);

            var loginBody = new HashMap<>();
            loginBody.put("username", "newUser");
            loginBody.put("password", "Password1!!");

            // when
            var target = 로그인_요청(loginBody);

            // then
            Assertions.assertAll(
                    () -> assertThat(target.statusCode()).isEqualTo(401),
                    () -> assertThat(target.body().jsonPath().getString("message")).isEqualTo(
                            "유저 이름 또는 비밀번호가 일치하지 않습니다.")
            );
        }
    }
}
