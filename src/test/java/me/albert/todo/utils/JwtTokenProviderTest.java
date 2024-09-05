package me.albert.todo.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JWT 토큰 생성기 테스트")
public class JwtTokenProviderTest {

    long accessTokenExpiration = 1000 * 60 * 60;
    long refreshTokenExpiration = 1000 * 60 * 60 * 24;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider(accessTokenExpiration, refreshTokenExpiration);
    }

    @DisplayName("Access Token 생성 테스트")
    @Test
    void generate_access_token() {
        // given
        var signTime = LocalDateTime.now();
        var token = jwtTokenProvider.generateAccessToken("testUser", signTime);

        // then
        assertThat(token).isNotNull();
    }


    @DisplayName("Refresh Token 생성 테스트")
    @Test
    void generate_refresh_token() {
        // given
        var signTime = LocalDateTime.now();
        var token = jwtTokenProvider.generateRefreshToken("testUser", signTime);

        // then
        assertThat(token).isNotNull();
    }

    @DisplayName("토큰에서 유저 이름 추출 테스트")
    @Test
    void get_username_from_token() {
        // given
        var signTime = LocalDateTime.now();
        var expect = "testUser";
        var token = jwtTokenProvider.generateAccessToken(expect, signTime);

        // when
        var target = jwtTokenProvider.getUsername(token);

        // then
        assertThat(target).isEqualTo(expect);
    }

    @DisplayName("토큰 유효성 검사 성공 테스트")
    @Test
    void validate_token_success() {
        // given
        var signTime = LocalDateTime.now();
        var token = jwtTokenProvider.generateAccessToken("testUser",signTime);

        // when
        var result = jwtTokenProvider.validateToken(token);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("토큰 유효성 검사 실패 테스트")
    @Test
    void validate_token_fail() {
        // given
        String token = "invalid";

        // when
        boolean result = jwtTokenProvider.validateToken(token);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("토큰 만료 검사 성공 테스트")
    @Test
    void validate_token_expired_success() {
        // given
        var signTime = LocalDateTime.now().minusSeconds(accessTokenExpiration);
        var token = jwtTokenProvider.generateAccessToken("testUser", signTime);

        // when
        var result = jwtTokenProvider.validateToken(token);

        // then
        assertThat(result).isFalse();
    }
}
