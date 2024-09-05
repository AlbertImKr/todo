package me.albert.todo.resolver;

import me.albert.todo.controller.CurrentUsername;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentUsernameArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUsername.class);
    }

    @Override
    public String resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory
    ) {
        var username = webRequest.getAttribute("username", NativeWebRequest.SCOPE_REQUEST);
        if (username == null) {
            throw new IllegalArgumentException("유저 네임 오류 발생");
        }
        if (!(username instanceof String)) {
            throw new IllegalArgumentException("유저 네임 오류 발생");
        }
        return (String) username;
    }
}
