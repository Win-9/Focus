package org.example.focus.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;

@Getter
@Builder
public class ImageRequestDto {
    private String title;
    private String form;
    private String extension;
    private int page;

    public static ImageRequestDto of(Book book) {
        return ImageRequestDto.builder()
                .title(book.getTitle())
                .form("bookCover")
                .extension(book.getExtension())
                .build();
    }

    public static ImageRequestDto of(BookMark bookMark) {
        return ImageRequestDto.builder()
                .title(bookMark.getBook().getTitle())
                .form("bookMark")
                .extension(bookMark.getExtension())
                .page(bookMark.getPage())
                .build();
    }


}
