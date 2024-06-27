package org.example.focus.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.example.focus.repository.BookMarkRepository;
import org.example.focus.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DataInitializationService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMarkRepository bookMarkRepository;

    private final Random random = new Random();

    @Transactional
//    @PostConstruct
    public void initializeData() {
        final int batchSize = 1000;
        final int bookMarkBatchSize = 300;

        for (int i = 0; i < 30000; i += batchSize) {
            List<Book> books = new ArrayList<>();
            for (int j = 0; j < batchSize; j++) {
                Book book = Book.builder()
                        .title("Book Title " + getRandomString(5))
                        .author("Author " + getRandomString(7))
                        .coverImage("Cover Image " + getRandomString(10))
                        .registeredDate(LocalDate.now().minusDays(random.nextInt(365)))
                        .modifiedDate(LocalDate.now().minusDays(random.nextInt(365)))
                        .extension("Extension " + getRandomString(3))
                        .build();
                books.add(book);
            }
            bookRepository.saveAll(books);

            for (Book book : books) {
                List<BookMark> bookMarks = new ArrayList<>();
                for (int k = 0; k < bookMarkBatchSize; k++) {
                    BookMark bookMark = BookMark.builder()
                            .date(LocalDate.now().minusDays(random.nextInt(365)))
                            .page(random.nextInt(500))
                            .thumbnailImage("Thumbnail Image " + getRandomString(8))
                            .text("Text " + getRandomString(20))
                            .extension("Extension " + getRandomString(3))
                            .modifiedDate(LocalDate.now().minusDays(random.nextInt(365)))
                            .book(book)
                            .build();
                    bookMarks.add(bookMark);
                }
                bookMarkRepository.saveAll(bookMarks);
            }
        }
    }


    private String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }
}