package com.devteria.bugtracking.exception;

import com.devteria.bugtracking.dto.ApiResponse;
import com.devteria.bugtracking.dto.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> exception(Exception e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.USER_EXISTED.getMessage());
        apiResponse.setCode(ErrorCode.USER_EXISTED.getCode());

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
