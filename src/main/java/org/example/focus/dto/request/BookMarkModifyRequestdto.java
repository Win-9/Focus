package org.example.focus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookMarkModifyRequestdto {
    @NotNull(message = "내용 입력은 필수입니다.")
    @NotBlank(message = "내용 입력은 필수입니다.")
    private String content;

    @NotNull(message = "페이지 입력은 필수입니다.")
    @NotBlank(message = "페이지 입력은 필수입니다.")
    private int page;
}
