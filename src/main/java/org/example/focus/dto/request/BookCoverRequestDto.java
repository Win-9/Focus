package org.example.focus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookCoverRequestDto {
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String author;
}
