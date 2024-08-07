package org.example.focus.dto.resopnse;

import lombok.*;
import org.example.focus.entity.BookMark;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllBookMarkResponseDto {
    private String id;
    private String thumbnailImage;
    private LocalDate date;
    private int page;
    private BookResponseDto book;

    public static AllBookMarkResponseDto from(BookMark bookMark) {
        return AllBookMarkResponseDto.builder()
                .id(String.valueOf(bookMark.getId()))
                .thumbnailImage(bookMark.getThumbnailImage())
                .date(bookMark.getModifiedDate())
                .page(bookMark.getPage())
                .book(BookResponseDto.from(bookMark.getBook()))
                .build();
    }
}
