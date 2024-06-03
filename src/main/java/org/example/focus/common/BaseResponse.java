package org.example.focus.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseResponse <T> {
    private String message;

    @JsonIgnore
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
