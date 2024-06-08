package org.example.focus.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageRequestDto {
    private String title;
    private String form;
    private String extension;
    private int page;

    public static ImageRequestDto of(BookCoverRequestDto requestDto, String extension) {
        return ImageRequestDto.builder()
                .title(requestDto.getTitle())
                .form("bookCover")
                .extension(extension)
                .build();
    }

    public static ImageRequestDto of(BookMarkRequestDto requestDto, String extension) {
        return ImageRequestDto.builder()
                .title(requestDto.getTitle())
                .form("bookMark")
                .extension(extension)
                .page(requestDto.getPage())
                .build();
    }
}
