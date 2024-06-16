package org.example.focus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXIST_BOOK(HttpStatus.BAD_REQUEST, "이미 존재하는 책 입니다."),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 책 입니다.");

    private HttpStatus httpStatus;
    private String message;
}
