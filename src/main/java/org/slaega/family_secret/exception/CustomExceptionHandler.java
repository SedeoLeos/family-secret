package org.slaega.family_secret.exception;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import lombok.Data;

@ControllerAdvice
@Data
public class CustomExceptionHandler {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorDetails error = new ErrorDetails();
        error.setCode(HttpStatus.BAD_REQUEST);
        List<String> collect = ex.getBindingResult().getFieldErrors().stream().filter(Objects::nonNull)
                .map(m -> (m.getField() + " " + m.getDefaultMessage())).toList();
        List<String> message = new ArrayList<>(collect);
        error.setMessage(message);
        return new ResponseEntity<>(error, HttpStatus.NOT_EXTENDED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        System.out.println("\n\n\n ---------------"+ex+"\n\n\n ---------------");
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleAllExceptions(ResponseStatusException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status",ex.getBody().getStatus());
        body.put("error", ex.getStatusCode());
        body.put("message", ex.getBody().getDetail());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, ex.getStatusCode());
    }



}
