package me.albert.todo.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import me.albert.todo.controller.CurrentUsername;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

@DisplayName("CurrentUsernameArgumentResolver 테스트")
public class CurrentUsernameArgumentResolverTest {

    private CurrentUsernameArgumentResolver resolver;
    private NativeWebRequest webRequest;

    @BeforeEach
    void setUp() {
        resolver = new CurrentUsernameArgumentResolver();
        webRequest = mock(NativeWebRequest.class);
    }

    @DisplayName("파라미터에 CurrentUsername 어노테이션이 있을 때 true를 반환한다")
    @Test
    void supports_parameter_returns_true_when_parameter_has_current_username_annotation() {
        // given
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(CurrentUsername.class)).thenReturn(true);

        // when
        var target = resolver.supportsParameter(parameter);

        // then
        assertThat(target).isTrue();
    }

    @DisplayName("파라미터에 CurrentUsername 어노테이션이 없을 때 false를 반환한다")
    @Test
    void supports_parameter_returns_false_when_parameter_does_not_have_current_username_annotation() {
        // given
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(CurrentUsername.class)).thenReturn(false);

        // when
        var target = resolver.supportsParameter(parameter);

        // then
        assertThat(target).isFalse();
    }

    @DisplayName("resolveArgument는 요청 속성에 username이 있을 때 username을 반환한다")
    @Test
    void resolve_argument_returns_username_when_present_in_request_attributes() {
        // given
        when(webRequest.getAttribute("username", NativeWebRequest.SCOPE_REQUEST)).thenReturn("testUser");

        // when
        var target = resolver.resolveArgument(null, null, webRequest, null);

        // then
        assertEquals("testUser", target);
    }

    @DisplayName("resolveArgument는 요청 속성에 username이 없을 때 예외를 발생시킨다")
    @Test
    void resolve_argument_throws_exception_when_username_not_present_in_request_attributes() {
        // given
        when(webRequest.getAttribute("username", NativeWebRequest.SCOPE_REQUEST)).thenReturn(null);

        // when, then
        assertThatThrownBy(() -> resolver.resolveArgument(null, null, webRequest, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저 네임 오류 발생");
    }

    @DisplayName("resolveArgument는 username이 문자열이 아닐 때 예외를 발생시킨다")
    @Test
    void resolve_argument_throws_exception_when_username_not_a_string() {
        // given
        when(webRequest.getAttribute("username", NativeWebRequest.SCOPE_REQUEST)).thenReturn(123);

        // when, then
        assertThatThrownBy(() -> resolver.resolveArgument(null, null, webRequest, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유저 네임 오류 발생");
    }
}
