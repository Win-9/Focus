package org.example.focus.exception.notFound;

import lombok.Getter;
import org.example.focus.exception.ErrorCode;

@Getter
public class FileBoundException extends NotFoundException{
    public FileBoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
