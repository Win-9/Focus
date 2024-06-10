package org.example.focus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.focus.dto.request.BookMarkModifyRequestdto;

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

    public void changeBookMarkInfo(BookMarkModifyRequestdto request) {
        this.text = request.getText();
        this.page = request.getPage();
    }

    public void changeExtension(String extension) {
        this.extension = extension;
    }
}
