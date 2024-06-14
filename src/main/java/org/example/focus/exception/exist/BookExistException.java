package org.example.focus.exception.exist;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class BookExistException extends ExistException {
    public BookExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
