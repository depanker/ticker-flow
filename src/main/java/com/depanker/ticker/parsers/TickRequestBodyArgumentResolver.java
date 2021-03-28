package com.depanker.ticker.parsers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.stream.Collectors;

/**
 * This class will resolve the custome
 * format of the ticker
 */
@RequiredArgsConstructor
public class TickRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

    private final TickerParser ricTickerParser;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(TickRequestBody.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        String requestBody = ((ServletWebRequest) webRequest)
                .getRequest()
                .getReader()
                .lines().collect(Collectors.joining(System.lineSeparator()));
        return ricTickerParser.parse(requestBody, null);
    }
}
