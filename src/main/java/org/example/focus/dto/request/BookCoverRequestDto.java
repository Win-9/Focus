package org.example.focus.dto.request;

import lombok.Getter;

@Getter
public class BookCoverRequestDto {
    private String title;
    private String author;
    private String form;
    private String extension;
    private int page;
}
