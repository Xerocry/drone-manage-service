package com.xerocry.dronemanageservice.config;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationHandler(MethodArgumentNotValidException exception) {
        HashMap<String, Object> resObj = new HashMap<>();
        String errorMsg = "Drone validation is failed!";
        if (exception.getErrorCount() > 0) {
            List <String> errorDetails = new ArrayList<>();
            for (ObjectError error : exception.getBindingResult().getAllErrors()) {
                errorDetails.add(error.getDefaultMessage());
            }

            if (errorDetails.size() > 0) errorMsg = errorDetails.get(0);
        }

        resObj.put("Status", HttpStatus.BAD_REQUEST);
        resObj.put("Response code", HttpStatus.BAD_REQUEST.value());
        resObj.put("Message", errorMsg);
        return new ResponseEntity<>(resObj, HttpStatus.OK);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        ApiError apiError =
                new ApiError(HttpStatus.NO_CONTENT, ex.getLocalizedMessage(), "Drone is empty!");
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}

@Getter
class ApiError {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }
}