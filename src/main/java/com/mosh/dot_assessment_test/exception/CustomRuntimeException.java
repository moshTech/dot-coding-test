package com.mosh.dot_assessment_test.exception;

import com.mosh.dot_assessment_test.response.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */

@Getter
public class CustomRuntimeException extends RuntimeException {
    private String code = "SER500";


    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    private List<ApiError> errors = new ArrayList<>();
    public CustomRuntimeException() {
    }

    public CustomRuntimeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public CustomRuntimeException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public CustomRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomRuntimeException(String message, HttpStatus httpStatus, List<ApiError> errors) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public CustomRuntimeException(HttpStatus httpStatus, List<ApiError> errors) {
        super("unable to process request");
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public CustomRuntimeException(HttpStatus httpStatus, ApiError error) {
        super("unable to process request");
        this.httpStatus = httpStatus;
        this.errors = List.of(error);
    }

    public CustomRuntimeException(ApiError error) {
        super("unable to process request");
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errors = List.of(error);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public String getCode() {
        return code;
    }

    public CustomRuntimeException(String message, Throwable inner) {
        super(message, inner);
    }

    public CustomRuntimeException(String message) {
        super(message);
    }


}


