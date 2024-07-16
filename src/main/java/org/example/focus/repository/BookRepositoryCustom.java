package org.example.focus.repository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepositoryCustom {
    List<LocalDate> findAllBookToLocalDate();
}
