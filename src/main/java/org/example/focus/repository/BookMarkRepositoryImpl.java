package org.example.focus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;

import java.time.LocalDate;
import java.util.List;

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
    public List<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc() {
        return queryFactory
                .select(Projections.fields(AllBookMarkResponseDto.class,
                        bookMark.id.as("id"),
                        bookMark.modifiedDate.as("date")))
                .from(bookMark)
                .orderBy(bookMark.modifiedDate.desc())
                .fetch();
    }
}
