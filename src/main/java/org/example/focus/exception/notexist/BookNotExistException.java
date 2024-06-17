package org.example.focus.exception.notexist;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class BookNotExistException extends NotExistException{
    public BookNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
