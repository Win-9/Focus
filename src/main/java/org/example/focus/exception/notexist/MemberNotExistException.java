package org.example.focus.exception.notexist;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class MemberNotExistException extends NotExistException{

    public MemberNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
