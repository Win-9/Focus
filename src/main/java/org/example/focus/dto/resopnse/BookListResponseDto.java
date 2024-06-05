package org.example.focus.dto.resopnse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.entity.Book;

@Builder
@AllArgsConstructor
@Getter
public class BookListResponseDto {
    private String title;
    private String author;
    private String coverImage;

    public static BookListResponseDto from(Book book) {
        return BookListResponseDto.builder()
                .author(book.getTitle())
                .author(book.getAuthor())
                .coverImage(book.getCoverImage())
                .build();
    }
}
