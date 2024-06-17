package org.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.focus.dto.request.BookCoverRequestDto;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String author;
    private String coverImage;
    private LocalDateTime registeredDate;
    private LocalDateTime modifiedDate;
    private String extension;

    public void changeBookInformation(BookCoverRequestDto requset) {
        this.title = requset.getTitle();
        this.author = requset.getAuthor();
        modifiedDate = LocalDateTime.now();
    }

    public void changeExtension(String extension) {
        this.extension = extension;
    }

    public void changeCoverImage(String converImage) {
        this.coverImage = converImage;
    }
}
