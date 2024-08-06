package org.example.focus.repository;

import org.example.focus.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {

    List<BookMark> findAllByBookIdOrderByModifiedDateAsc(Long bookId);

    List<BookMark> findAllByModifiedDateBetween(LocalDate startDate, LocalDate endDate);
}
