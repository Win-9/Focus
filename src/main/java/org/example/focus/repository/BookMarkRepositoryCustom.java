package org.example.focus.repository;

import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BookMarkRepositoryCustom {
    long findBookMarkCountByLocalDate(LocalDate localDate);
    Page<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc(Pageable pageable, Long count);
}
