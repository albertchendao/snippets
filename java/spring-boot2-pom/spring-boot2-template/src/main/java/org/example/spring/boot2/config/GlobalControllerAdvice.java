package org.example.spring.boot2.config;

import org.example.spring.boot2.resp.ResultResp;
import org.example.spring.boot2.resp.ResultRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/20 10:28
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalControllerAdvice {

    private void errorLog(Exception e) {
        log.error("GlobalControllerAdvice Exception", e);
    }

    @ExceptionHandler({Exception.class})
    public ResultResp<?> exception(Exception e) {
        errorLog(e);
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, e.getMessage());
    }

    /**
     * HttpMessageNotReadableException.class Body无法编译异常
     * TypeMismatchException.class Path params format失败异常
     * MissingServletRequestParameterException.class 缺少RequestParams异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResultResp<?> httpMessageNotReadableException(Exception e) {
        errorLog(e);
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResultResp<?> httpMediaTypeException(HttpMediaTypeException e) {
        errorLog(e);
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultResp<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        errorLog(e);
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResp<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResultRespFactory.error(ResultRespFactory.INVALID_PARAM, msg);
    }

    @ExceptionHandler(BindException.class)
    public ResultResp<?> bindException(BindException e) {
        String msg = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return ResultRespFactory.error(ResultRespFactory.INVALID_PARAM, msg);
    }

    @ExceptionHandler(ValidationException.class)
    public ResultResp<?> validationException(ValidationException e) {
        return ResultRespFactory.error(ResultRespFactory.INVALID_PARAM, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResultResp<?> constraintViolationException(ConstraintViolationException e) {
        String error = "";
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        if (violations != null && violations.size() > 0) {
            error = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        }
        return ResultRespFactory.error(ResultRespFactory.INVALID_PARAM, error);
    }

}