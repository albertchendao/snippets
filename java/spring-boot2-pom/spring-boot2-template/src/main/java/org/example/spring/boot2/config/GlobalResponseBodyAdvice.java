package org.example.spring.boot2.config;

import lombok.extern.slf4j.Slf4j;
import org.example.common.helper.JsonHelper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 1. 通过 @ControllerAdvice 注入
 * 2. 可以通过 DelegatingWebMvcConfiguration 注入
 *
 * public class AppResponseConfiguration extends DelegatingWebMvcConfiguration {
 *         @Bean
 *     public GlobalResponseBodyAdvice globalResponseBodyAdvice() {
 *         return new GlobalResponseBodyAdvice();
 *     }
 *
 *      @Bean
 *      @Override
 *      public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
 *          RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.requestMappingHandlerAdapter();
 *
 *          List<ResponseBodyAdvice<?>> responseBodyAdvices = new ArrayList<>();
 *          responseBodyAdvices.add(globalResponseBodyAdvice());
 *          requestMappingHandlerAdapter.setResponseBodyAdvice(responseBodyAdvices);
 *
 *          return requestMappingHandlerAdapter;
 *      }
 * }
 */
@Slf4j
@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("body: {}", JsonHelper.toString(body));
        log.info("body class: {}", body != null ? body.getClass() : null);
        log.info("MethodParameter.getParameterType(): {}", methodParameter.getParameterType());
        return body;
    }
}
