package org.slaega.family_secret.exception;



import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.*;

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


}
