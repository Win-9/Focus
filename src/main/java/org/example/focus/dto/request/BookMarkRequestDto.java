package org.example.focus.dto.request;

import lombok.Getter;

@Getter
public class BookMarkRequestDto {
    private long bookId;
    private String content;
    private int page;
}

