package org.slaega.family_secret.exception;

import lombok.Data;
import org.slaega.family_secret.passwordless.util.AuthErrors;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiExceptionHandler extends RuntimeException {
    private List<AuthErrors.Error> errorMessages;
    private HttpStatus status;

    public ApiExceptionHandler(List<AuthErrors.Error> errorMessages,HttpStatus status) {
        super();
        this.errorMessages = errorMessages != null ? errorMessages : new ArrayList<>();
        this.status = status;
    }
    public ApiExceptionHandler(HttpStatus status) {
        super();
        this.errorMessages =  new ArrayList<>();
        this.status = status;
    }


}
