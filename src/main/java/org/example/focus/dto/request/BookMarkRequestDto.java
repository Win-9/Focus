package org.example.focus.dto.request;

import lombok.Getter;

@Getter
public class BookMarkRequestDto {
    private String bookId;
    private String content;
    private int page;
}

