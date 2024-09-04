package me.albert.todo.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.albert.todo.utils.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthInterceptorTest {

    private JwtTokenProvider jwtTokenProvider;
    private AuthInterceptor authInterceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object handler;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = mock(JwtTokenProvider.class);
        authInterceptor = new AuthInterceptor(jwtTokenProvider);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        handler = new Object();
    }

    @DisplayName("토큰이 유효하면 true를 반환한다")
    @Test
    void pre_handle_valid_token_returns_true() throws Exception {
        // given
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtTokenProvider.validateToken("validToken")).thenReturn(true);
        when(jwtTokenProvider.getUsername("validToken")).thenReturn("testUser");

        // when
        boolean result = authInterceptor.preHandle(request, response, handler);

        // then
        assertThat(result).isTrue();
        verify(request).setAttribute("username", "testUser");
    }

    @DisplayName("토큰이 유효하지 않으면 false를 반환한다")
    @Test
    void pre_handle_invalid_token_returns_false() throws Exception {
        // given
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(jwtTokenProvider.validateToken("invalidToken")).thenReturn(false);

        // when
        boolean result = authInterceptor.preHandle(request, response, handler);

        // then
        assertThat(result).isFalse();
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @DisplayName("토큰이 없으면 false를 반환한다")
    @Test
    void preHandle_noToken_returnsFalse() throws Exception {
        // given
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        boolean result = authInterceptor.preHandle(request, response, handler);

        // then
        assertThat(result).isFalse();
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @DisplayName("Bearer 토큰이 아닌 경우 false를 반환한다")
    @Test
    void preHandle_tokenWithoutBearer_returnsFalse() throws Exception {
        // given
        when(request.getHeader("Authorization")).thenReturn("invalidToken");

        // when
        boolean result = authInterceptor.preHandle(request, response, handler);

        // then
        assertThat(result).isFalse();
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
