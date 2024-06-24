package org.example.focus.repository;

import org.example.focus.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    List<BookMark> findAllByBookIdOrderByDateAsc(Long bookId);

    List<BookMark> findAllByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
