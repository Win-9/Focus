package org.example.focus.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.focus.common.BaseErrorResopnse;
import org.example.focus.exception.exist.ExistException;
import org.example.focus.exception.notFound.NotFoundException;
import org.example.focus.exception.notexist.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResopnse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        return new ResponseEntity<>(new BaseErrorResopnse(
                HttpStatus.BAD_REQUEST.value(),
                bindingResult.getFieldErrors().get(0).getDefaultMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistException.class)
    public ResponseEntity<BaseErrorResopnse> handleNotExistException(ExistException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new BaseErrorResopnse(e.getStatus(), e.getErrorMessage()),
                HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseErrorResopnse> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new BaseErrorResopnse(e.getStatus(), e.getErrorMessage()),
                HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<BaseErrorResopnse> handleNotFoundException(NotExistException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new BaseErrorResopnse(e.getStatus(), e.getErrorMessage()),
                HttpStatus.valueOf(e.getStatus()));
    }
}
