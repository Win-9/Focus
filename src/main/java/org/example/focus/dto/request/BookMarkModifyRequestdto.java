package org.example.focus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookMarkModifyRequestdto {
    @NotNull
    @NotBlank
    private String content;

    @NotNull
    @NotBlank
    private int page;
}
