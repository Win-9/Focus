package org.example.focus.exception.notexist;

import org.example.focus.exception.ErrorCode;

public class BookMarkNotExistException extends NotExistException{
    public BookMarkNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
