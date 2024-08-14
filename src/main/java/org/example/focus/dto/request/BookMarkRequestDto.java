package org.example.focus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookMarkRequestDto {
    @NotNull
    @NotBlank
    private String bookId;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    @NotBlank
    private int page;
}

