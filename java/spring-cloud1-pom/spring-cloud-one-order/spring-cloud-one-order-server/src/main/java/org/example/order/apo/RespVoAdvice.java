package org.example.order.apo;

import lombok.extern.slf4j.Slf4j;
import org.example.common.enumeration.RespCode;
import org.example.common.exception.ServiceException;
import org.example.common.vo.RespVo;
import org.example.order.helper.HttpContextHelper;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

@Slf4j
@RestControllerAdvice({"org.example.two.controller"})
public class RespVoAdvice implements ResponseBodyAdvice<Object> {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public RespVo<?> unknownErrorHandler(Exception e) {
        log.error("catch exception", e);
        return HttpContextHelper.error(RespCode.SERVER_ERROR);
    }

    @ExceptionHandler({ServiceException.class})
    public RespVo<?> serviceErrorHandler(ServiceException e) {
        log.error("catch exception", e);
        return HttpContextHelper.error(e.getErrorCode(), e.getMessage());
    }

    /**
     * HttpMessageNotReadableException.class Body无法编译异常
     * TypeMismatchException.class Path params format失败异常
     * MissingServletRequestParameterException.class 缺少RequestParams异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class, MissingServletRequestParameterException.class})
    public RespVo<?> parameterErrorHandler(Exception e) {
        log.error("catch exception", e);
        return HttpContextHelper.error(RespCode.PARAMETER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public RespVo<?> httpMediaTypeErrorHandler() {
        return HttpContextHelper.error(RespCode.HTTP_MEDIA_TYPE_NOT_SUPPORT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespVo<?> methodNotSupport(HttpRequestMethodNotSupportedException e) {
        log.error("method error", e);
        return HttpContextHelper.error(RespCode.REQUEST_METHOD_NOT_SUPPORT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespVo<?> methodArgumentNotValidErrorHandler(MethodArgumentNotValidException e) {
        log.error("paramter error", e);
        BindingResult result = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            String field = error.getCode();
            String code = error.getDefaultMessage();
            String message = String.format("%s:%s", field, code);
            sb.append(message);
        }
        return HttpContextHelper.error(RespCode.PARAMETER_ERROR, sb.toString());

    }

    @ExceptionHandler(ValidationException.class)
    public RespVo<?> methodArgumentNotValidErrorHandler(ValidationException e) {
        log.error("paramter error", e);
        return HttpContextHelper.error(RespCode.PARAMETER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RespVo<?> methodArgumentNotValidErrorHandler(ConstraintViolationException e) {
        log.error("paramter error", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        if (violations != null && violations.size() > 0) {
            String error = violations.stream().map(ConstraintViolation::getMessage).reduce((o1, o2) -> o1 + ";" + o2).get();
            log.error("paramter error", error);
            return HttpContextHelper.error(RespCode.PARAMETER_ERROR, error);
        } else {
            return HttpContextHelper.error(RespCode.PARAMETER_ERROR);
        }
    }

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class retureTypeClass = returnType.getMethod().getReturnType();
        return !String.class.equals(retureTypeClass);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }
}
