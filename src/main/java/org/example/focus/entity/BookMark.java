package org.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.focus.dto.request.BookMarkModifyRequestdto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_mark", indexes = {
        @Index(name = "idx_modified_date", columnList = "modifiedDate")
})
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private int page;
    private String thumbnailImage;
    private String text;
    private String extension;
    private LocalDate modifiedDate;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public void changeBookMarkInfo(BookMarkModifyRequestdto request) {
        this.text = request.getText();
        this.page = request.getPage();
    }

    public void changeExtension(String extension) {
        this.extension = extension;
    }

    public void changeBook(Book book) {
        this.book = book;
    }

    public void changeModifiedDate(LocalDate now) {
        this.modifiedDate = now;
    }

    public void changeThubnail(String thumbnail) {
        this.thumbnailImage = thumbnail;
    }
}
