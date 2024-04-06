package com.bizremark.institution.common.configuration;

import com.bizremark.institution.common.constants.AppConstants;
import com.bizremark.institution.common.constants.ErrorConstants;
import org.apache.ignite.IgniteException;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IgniteException.class)
    @ResponseBody
    public ResponseEntity<Object> igniteErrorResponse(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorConstants.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> genericErrorResponse(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorConstants.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ApplicationContextException.class)
    public ResponseEntity<Object> handleApplicationContextException(ApplicationContextException applicationContextException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(AppConstants.TIMESTAMP, LocalDateTime.now());
        body.put(AppConstants.STATUS, HttpStatus.BAD_REQUEST);
        body.put(AppConstants.ERROR, applicationContextException.getCause());
        body.put(AppConstants.MESSAGE, applicationContextException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(AppConstants.TIMESTAMP, LocalDateTime.now());
        body.put(AppConstants.STATUS, ex.getStatusCode().value());
        body.put(AppConstants.ERROR, ex.getMessage());
        body.put(AppConstants.MESSAGE, ex.getReason());
        body.put(AppConstants.PATH, request.getContextPath());

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}

