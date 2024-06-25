package org.example.focus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static org.example.focus.entity.QBookMark.*;

@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public long findBookMarkCountByLocalDate(LocalDate localDate) {
        return queryFactory
                .select(bookMark.count())
                .from(bookMark)
                .where(bookMark.modifiedDate.eq(localDate.atStartOfDay()))
                .fetchOne();
    }
}
