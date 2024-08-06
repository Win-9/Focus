package org.example.focus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseErrorResopnse {
    private int status;
    private String message;
}
