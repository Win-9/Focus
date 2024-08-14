package org.example.focus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXIST_BOOK(HttpStatus.BAD_REQUEST, "이미 존재하는 책 입니다."),
    EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "옳바르지 않는 확장자 형식입니다."),
    BOOK_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 책 입니다."),
    BOOKMAKR_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 책갈피 입니다."),
    IMAGE_SAVE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패하였습니다."),
    LOGIN_EXCPETION(HttpStatus.UNAUTHORIZED, "권한이 없습니다")

    ;

    private HttpStatus httpStatus;
    private String message;
}
