package org.example.focus.repository;

import org.example.focus.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    List<Book> findAllByModifiedDateBetween(LocalDate startDate, LocalDate endDate);

    List<Book> findAllByOrderByModifiedDateDesc();

    boolean existsByTitle(String title);
}
