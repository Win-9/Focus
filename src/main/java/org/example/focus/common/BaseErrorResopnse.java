package org.example.focus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class BaseErrorResopnse {
    private int status;
    private String message;
}
