package org.example.focus.repository;

import org.example.focus.dto.resopnse.AllBookMarkResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface BookMarkRepositoryCustom {
    long findBookMarkCountByLocalDate(LocalDate localDate);
    List<AllBookMarkResponseDto> findAllByOrderByModifiedDateDesc();
}
