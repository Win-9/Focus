package org.example.focus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
@AllArgsConstructor
public class BaseErrorResopnse {
    private int status;
    private String message;

    public static BaseErrorResopnse of(ErrorCode errorCode) {
        return new BaseErrorResopnse(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }
}
