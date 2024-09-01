package org.example.focus.repository;

import org.example.focus.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @Query("SELECT b.modifiedDate FROM Book b WHERE b.member.id = :memberId AND b.modifiedDate BETWEEN :startDate AND :endDate")
    List<LocalDate> findAllLocalDateByMemberIdAndModifiedDateBetween(@Param("memberId") long memberId,
                                                                     @Param("startDate") LocalDate startDate,
                                                                     @Param("endDate") LocalDate endDate);

    List<Book> findAllByMemberIdOrderByModifiedDateDesc(long memberId);

    boolean existsByMemberIdAndTitle(long memberId, String title);
    Optional<Book> findByIdAndMemberId(long memberId, long id);
}
