package org.example.focus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.focus.entity.QBook;

import java.time.LocalDate;
import java.util.List;

import static org.example.focus.entity.QBook.book;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<LocalDate> findAllBookToLocalDate() {
        return queryFactory
                .select(book.modifiedDate)
                .from(book)
                .fetch();
    }
}
