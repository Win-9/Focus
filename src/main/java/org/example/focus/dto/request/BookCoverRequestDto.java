package org.example.focus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookCoverRequestDto {
    @NotNull(message = "제목 입력은 필수입니다.")
    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;

    @NotNull(message = "저자 입력은 필수입니다.")
    @NotBlank(message = "저자 입력은 필수입니다.")
    private String author;
}
