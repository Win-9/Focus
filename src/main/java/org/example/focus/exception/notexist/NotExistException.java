package org.example.focus.exception.notexist;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class NotExistException extends RuntimeException{
    private int status;
    private String errorMessage;
    public NotExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus().value();
        this.errorMessage = errorCode.getMessage();
    }
}
