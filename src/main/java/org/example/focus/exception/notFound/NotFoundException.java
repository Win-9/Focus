package org.example.focus.exception.notFound;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class NotFoundException extends RuntimeException {
    private int status;
    private String errorMessage;
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus().value();
        this.errorMessage = errorCode.getMessage();
    }
}
