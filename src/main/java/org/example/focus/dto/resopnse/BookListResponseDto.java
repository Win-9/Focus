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
    private Long id;
    private String title;
    private String author;
    private String coverImageUrl;

    public static BookListResponseDto from(Book book) {
        return BookListResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImageUrl(book.getCoverImage())
                .build();
    }
}
