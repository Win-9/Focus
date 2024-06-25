package org.example.focus.repository;

import org.example.focus.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Book> findAllByOrderByModifiedDateDesc();

    boolean existsByTitle(String title);
}