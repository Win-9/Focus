package org.example.focus.dto.resopnse;

import lombok.*;
import org.example.focus.entity.BookMark;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllBookMarkResponseDto {
    private Long id;
    private String thumbnailImage;
    private LocalDate date;
    private int page;

    public static AllBookMarkResponseDto from(BookMark bookMark) {
        return AllBookMarkResponseDto.builder()
                .id(bookMark.getId())
                .thumbnailImage(bookMark.getThumbnailImage())
                .date(bookMark.getModifiedDate())
                .page(bookMark.getPage())
                .build();
    }
}
