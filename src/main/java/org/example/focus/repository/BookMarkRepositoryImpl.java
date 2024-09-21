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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.example.focus.entity.QBookMark.*;
import static org.example.focus.entity.QMember.*;
import static org.example.focus.entity.QBook.*;


@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc(Pageable pageable, Long count, Long memberId) {
        List<AllBookMarkResponseDto> result = queryFactory
                .select(Projections.fields(AllBookMarkResponseDto.class,
                        bookMark.id.stringValue().as("id"),
                        bookMark.modifiedDate.as("date")))
                .from(bookMark)
                .join(member).on(member.id.eq(memberId))
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

    @Override
    public List<LocalDate> findAllLocalDateByMemberIdAndModifiedDateBetween(long memberId, LocalDate start, LocalDate end) {
        return queryFactory
                .select(bookMark.modifiedDate)
                .from(book)
                .join(book.member, member)
                .join(bookMark).on(bookMark.book.id.eq(book.id))
                .where(member.id.eq(memberId)
                        .and(bookMark.modifiedDate.goe(start))
                        .and(bookMark.modifiedDate.loe(end)))
                .fetch();

    }

    @Override
    public long findBookMarkCountByLocalDateMonth(int month) {
        return queryFactory
                .select()
                .from(bookMark)
                .where(bookMark.modifiedDate.month().eq(month))
                .fetchCount();
    }
}
