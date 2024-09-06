package com.mosh.dot_assessment_test.common;

import com.google.common.base.Strings;
import com.mosh.dot_assessment_test.response.ApiError;
import com.mosh.dot_assessment_test.response.ApiResponseJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
@Component
@RequiredArgsConstructor
@Slf4j
public final class CommonUtils {

    public static   ResponseEntity<Object> buildSuccessResponse(Object responseData, String message ){
        return ResponseEntity.ok(ApiResponseJson.builder()
                .success(true)

                .data(responseData)
                .message(Strings.isNullOrEmpty(message)?"Request Successfully Treated":message)
                .errors(null)
                .build());

    }

    public static   ResponseEntity<Object> buildSuccessResponse(Object responseData ){
        return ResponseEntity.ok(ApiResponseJson.builder()
                .success(true)
                .data(responseData)
                .message("Request Successfully Treated")
                .errors(null)
                .build());

    }

    public static   ResponseEntity<Object> buildSuccessResponse(Object responseData, Map<String,Object> metadata){
        return ResponseEntity.ok(ApiResponseJson.builder()
                .success(true)
                .data(responseData)
                .metadata(metadata)
                .message("Request Successfully Treated")
                .errors(null)
                .build());

    }

    public static   ResponseEntity<Object> buildSuccessResponse(){
        return ResponseEntity.ok(ApiResponseJson.builder()
                .success(true)

                .data(null)
                .message("Request Successfully Treated")
                .errors(null)
                .build());

    }



    public static ResponseEntity<Object> buildFailureResponse(List<ApiError> apiErrors, HttpStatus httpStatus ){
        String message = "";
        if (httpStatus.is4xxClientError()){
            message = "Kindly ensure you are passing the right information, check the errors field for what could be wrong with your request";
        }else if (httpStatus.is5xxServerError()){
            message = "Oops, sorry we were unable to process your request, reach out to support for help";
        }
        return new ResponseEntity<>(ApiResponseJson.builder()
                .success(false)
                .data(null)
                .message(message)
                .errors(apiErrors)
                .build(), httpStatus);

    }
}
