package com.mosh.dot_assessment_test.response;

import lombok.*;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiError {
    public ApiError(String message) {
        this.message = message;
    }

    public static final String ERROR_UNKNOWN = "90";

    private String message;
    private String code;
}
