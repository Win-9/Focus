package org.example.focus.dto.resopnse;

import lombok.Builder;
import lombok.Getter;
import org.example.focus.entity.Book;

@Getter
@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String coverImageUrl;

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImageUrl(book.getCoverImage())
                .build();
    }
}
