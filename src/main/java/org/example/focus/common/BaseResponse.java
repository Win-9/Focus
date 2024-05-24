package org.example.focus.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseResponse <T> {
    private String message;
    private T data;

    public BaseResponse(String message) {
        this.message = message;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("Data Successfully Transmitted", data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>("Data Successfully Transmitted");
    }
}
