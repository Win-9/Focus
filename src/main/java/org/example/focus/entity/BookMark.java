package org.example.focus.entity;

import jakarta.persistence.*;
import lombok.Getter;

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

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}
