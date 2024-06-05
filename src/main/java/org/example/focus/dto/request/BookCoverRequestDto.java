package org.example.focus.dto.request;

import lombok.Getter;

@Getter
public class BookCoverRequestDto {
    private String title;
    private String author;
    private String form;
    private int page;
}
