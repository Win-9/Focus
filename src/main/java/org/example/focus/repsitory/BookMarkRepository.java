package org.example.focus.repsitory;

import org.example.focus.entity.Book;
import org.example.focus.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    List<BookMark> findAllByBookIdOrderByDateAsc(Long bookId);
}
