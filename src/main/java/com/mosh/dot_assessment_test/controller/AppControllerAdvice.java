package com.mosh.dot_assessment_test.controller;

import com.mosh.dot_assessment_test.common.CommonUtils;
import com.mosh.dot_assessment_test.exception.CustomRuntimeException;
import com.mosh.dot_assessment_test.response.ApiError;
import com.mosh.dot_assessment_test.response.ApiResponseJson;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.mosh.dot_assessment_test.common.CommonUtils.buildFailureResponse;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@ControllerAdvice
@Slf4j
public class AppControllerAdvice {
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NoResourceFoundException ex) {
        log.info(ex.getMessage());
        return buildFailureResponse(List.of(new ApiError("Resource not found","404")), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.info("an error occurred: " + ex);
        return CommonUtils.buildFailureResponse(List.of(new ApiError(ex.getMethod().concat(" is not supported"))), HttpStatus.METHOD_NOT_ALLOWED);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.info("an error occurred: " + ex);
        return CommonUtils.buildFailureResponse(List.of(new ApiError("The required payload is missing","400")),HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {

        List<ApiError> errors = new ArrayList<>();
        List<ParameterValidationResult> results = ex.getAllValidationResults();
        for (ParameterValidationResult parameterError : results) {
            List<MessageSourceResolvable> fieldErrors = parameterError.getResolvableErrors();

            for (MessageSourceResolvable fieldError : fieldErrors) {
                ApiError error = new ApiError(fieldError.getDefaultMessage(), "400");
                errors.add(error);
            }
        }
        return CommonUtils.buildFailureResponse(errors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<Object> handleCustomRuntimeException(CustomRuntimeException exception) {
        log.info("an error occurred: " + (exception.getErrors().isEmpty()? exception.getMessage():exception.getErrors()));
        return CommonUtils.buildFailureResponse(exception.getErrors().isEmpty()? List.of(new ApiError(exception.getMessage())):exception.getErrors(),exception.getHttpStatus());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ApiError> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach((violation) -> {
            ApiError error = new ApiError(violation.getMessage(), "400");
            errors.add(error);
        });
        return CommonUtils.buildFailureResponse(errors,HttpStatus.BAD_REQUEST);
    }


    public boolean supports(MethodParameter mp, Class<? extends HttpMessageConverter<?>> type) {
        return true;
    }

    public Object beforeBodyWrite(Object body,
                                  MethodParameter mp,
                                  MediaType mt,
                                  Class<? extends HttpMessageConverter<?>> type,
                                  ServerHttpRequest shr,
                                  ServerHttpResponse shr1) {
        return body instanceof ApiResponseJson || body instanceof Resource ? this.cleanXSSObjectFields(body) :
                new ApiResponseJson();
    }

    protected Object cleanXSSObjectFields(Object body) {
        if (body == null) {
            return null;
        } else {
            if (!body.getClass().getPackageName().contains("com.mosh")) return body;
            Field[] fields = body.getClass().getDeclaredFields();
            Field[] var3 = fields;
            int var4 = fields.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field f = var3[var5];
                f.setAccessible(true);

                try {
                    Object value = f.get(body);
                    if (value instanceof String && !((String) value).isEmpty()) {
                        Object cleanValue = this.cleanXSS((String) value);
                        f.set(body, cleanValue);
                    }
                } catch (IllegalAccessException var9) {
                    log.info(var9.getMessage(), var9);
                }
            }
            return body;
        }
    }

    private String cleanXSS(String value) {
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
        value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
        value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
        value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
        value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
        return value;
    }
}
