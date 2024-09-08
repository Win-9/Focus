package org.example.focus.repository;

import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BookMarkRepositoryCustom {
    Page<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc(Pageable pageable, Long count);
    List<LocalDate> findAllBookMarkToLocalDate();
    List<LocalDate> findAllLocalDateByMemberIdAndModifiedDateBetween(long memberId, LocalDate start, LocalDate end);
    long findBookMarkCountByLocalDateMonth(int month);
}
