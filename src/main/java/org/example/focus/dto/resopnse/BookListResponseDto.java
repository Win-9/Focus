package org.example.focus.dto.resopnse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.focus.entity.Book;

@Builder
@AllArgsConstructor
@Getter
public class BookListResponseDto {
    private String id;
    private String title;
    private String author;
    private String coverImageUrl;

    public static BookListResponseDto from(Book book) {
        return BookListResponseDto.builder()
                .id(String.valueOf(book.getId()))
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImageUrl(book.getCoverImage())
                .build();
    }
}
