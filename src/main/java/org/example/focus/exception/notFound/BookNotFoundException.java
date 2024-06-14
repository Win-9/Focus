package org.example.focus.exception.notFound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.focus.exception.ErrorCode;

@Getter
public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
