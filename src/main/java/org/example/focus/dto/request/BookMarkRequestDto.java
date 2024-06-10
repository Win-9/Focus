package org.example.focus.dto.request;

import lombok.Getter;

@Getter
public class BookMarkRequestDto {
    private long bookId;
    private String title;
    private String text;
    private int page;
}

