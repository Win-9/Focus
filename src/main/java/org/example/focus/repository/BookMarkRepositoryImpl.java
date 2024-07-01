package org.example.focus.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
                .where(bookMark.modifiedDate.eq(localDate))
                .fetchOne();
    }

    @Override
    public Page<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc(Pageable pageable) {
        QueryResults<AllBookMarkResponseDto> result = queryFactory
                .select(Projections.fields(AllBookMarkResponseDto.class,
                        bookMark.id.as("id"),
                        bookMark.modifiedDate.as("date")))
                .from(bookMark)
                .orderBy(bookMark.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
