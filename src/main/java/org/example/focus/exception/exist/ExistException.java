package org.example.focus.exception.exist;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class ExistException extends RuntimeException {
    private int status;
    private String errorMessage;
    public ExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus().value();
        this.errorMessage = errorCode.getMessage();
    }
}
