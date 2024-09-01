package org.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.focus.dto.request.BookCoverRequestDto;

import java.time.LocalDate;

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
    private LocalDate registeredDate;
    private LocalDate modifiedDate;
    private String extension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeBookInformation(BookCoverRequestDto requset) {
        this.title = requset.getTitle();
        this.author = requset.getAuthor();
        modifiedDate = LocalDate.now();
    }

    public void changeExtension(String extension) {
        this.extension = extension;
    }

    public void changeCoverImage(String converImage) {
        this.coverImage = converImage;
    }
}
