package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;
import org.example.focus.entity.BookMark;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookMarkResponseDto {
    private Long id;
    private String thumbnailImage;
    private LocalDateTime date;
    private String text;
    private int page;

    public static BookMarkResponseDto from(BookMark bookMark) {
        return BookMarkResponseDto.builder()
                .id(bookMark.getId())
                .thumbnailImage(bookMark.getThumbnailImage())
                .date(bookMark.getDate())
                .page(bookMark.getPage())
                .text(bookMark.getText())
                .build();
    }
}
