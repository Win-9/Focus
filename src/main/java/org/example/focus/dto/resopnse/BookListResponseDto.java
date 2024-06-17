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
    private String coverImage;
    private long bookId;

    public static BookListResponseDto from(Book book) {
        return BookListResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImage(book.getCoverImage())
                .bookId(builder().bookId)
                .build();
    }
}
