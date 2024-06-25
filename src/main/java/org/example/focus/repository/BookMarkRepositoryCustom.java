package org.example.focus.repository;

import java.time.LocalDate;

public interface BookMarkRepositoryCustom {
    long findBookMarkCountByLocalDate(LocalDate localDate);
}
