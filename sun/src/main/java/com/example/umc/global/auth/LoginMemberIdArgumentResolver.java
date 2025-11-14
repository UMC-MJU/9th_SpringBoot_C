package com.example.umc.global.auth;

import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @LoginMemberId 어노테이션이 붙은 파라미터에 현재 로그인한 회원 ID를 주입하는 ArgumentResolver
 *
 * 현재는 간단하게 헤더에서 X-Member-Id를 추출하는 방식으로 구현
 * 추후 JWT 토큰 검증 로직으로 교체 가능
 */
@Component
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 헤더에서 회원 ID 추출 (추후 JWT 토큰 검증으로 교체 가능)
        String memberIdHeader = request.getHeader("X-Member-Id");

        if (memberIdHeader == null || memberIdHeader.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }

        try {
            return Long.parseLong(memberIdHeader);
        } catch (NumberFormatException e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
