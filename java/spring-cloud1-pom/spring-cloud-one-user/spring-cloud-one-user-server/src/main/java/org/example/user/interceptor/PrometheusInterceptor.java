package org.example.user.interceptor;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PrometheusInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MeterRegistry meterRegistry;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String requestURI = request.getRequestURI();
        final String method = request.getMethod();
        meterRegistry.counter("http_requests_total", "requestURI", requestURI, "method", method).increment();
        return true;
    }
}
