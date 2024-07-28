package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;
import org.example.focus.entity.BookMark;

import java.time.LocalDate;

@Getter
@Builder
public class BookMarkResponseDto {
    private Long id;
    private String thumbnailImage;
    private LocalDate date;
    private String text;
    private int page;
    private BookResponseDto book;

    public static BookMarkResponseDto from(BookMark bookMark) {
        return BookMarkResponseDto.builder()
                .id(bookMark.getId())
                .thumbnailImage(bookMark.getThumbnailImage())
                .date(bookMark.getDate())
                .page(bookMark.getPage())
                .text(bookMark.getContent())
                .book(BookResponseDto.from(bookMark.getBook()))
                .build();
    }
}
