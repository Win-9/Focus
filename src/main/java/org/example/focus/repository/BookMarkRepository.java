package org.example.focus.repository;

import org.example.focus.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {

    List<BookMark> findAllByBookIdOrderByModifiedDateAsc(Long bookId);

    @Query(
            "SELECT b FROM BookMark b WHERE b.book.member.id = :memberId AND b.id = :id"
    )
    Optional<BookMark> findBymemberIdAndId(Long memberId, Long id);
}
