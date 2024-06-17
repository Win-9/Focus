package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;
import org.example.focus.entity.BookMark;

import java.time.LocalDate;

@Getter
@Builder
public class AllBookMarkResponseDto {
    private Long id;
    private String thumbnailImage;
    private LocalDate date;
    private int page;

    public static AllBookMarkResponseDto from(BookMark bookMark) {
        return AllBookMarkResponseDto.builder()
                .id(bookMark.getId())
                .thumbnailImage(bookMark.getThumbnailImage())
                .date(bookMark.getDate())
                .page(bookMark.getPage())
                .build();
    }
}
