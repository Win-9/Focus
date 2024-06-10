package org.example.focus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.focus.dto.request.BookMarkRequestDto;

import java.time.LocalDateTime;

@Entity
@Getter
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime date;
    private int page;
    private String thumbnailImage;
    private String text;
    private String extension;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public void changeBookMarkInfo(BookMarkRequestDto request) {
        this.text = request.getText();
        this.page = request.getPage();
    }
}
