package org.example.focus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXIST_BOOK(HttpStatus.BAD_REQUEST, "Book is already exsit"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "Not exist book");

    private HttpStatus httpStatus;
    private String message;
}
