package org.example.focus.dto.resopnse;

import lombok.Builder;
import org.example.focus.entity.Book;

@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String coverImage;

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImage(book.getCoverImage())
                .build();
    }
}
