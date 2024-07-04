package org.example.focus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.entity.BookMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public Page<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc(Pageable pageable, Long count) {
        List<AllBookMarkResponseDto> result = queryFactory
                .select(Projections.fields(AllBookMarkResponseDto.class,
                        bookMark.id.as("id"),
                        bookMark.modifiedDate.as("date")))
                .from(bookMark)
                .orderBy(bookMark.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<BookMark> countQuery = queryFactory
                .select(bookMark)
                .from(bookMark);
        long totalCount = Optional.ofNullable(count).orElseGet(countQuery::fetchCount);
        return new PageImpl<>(result, pageable, totalCount);
    }

    @Override
    public List<LocalDate> findAllBookMarkToLocalDate() {
        return queryFactory
                .select(bookMark.modifiedDate)
                .from(bookMark)
                .fetch();
    }
}
