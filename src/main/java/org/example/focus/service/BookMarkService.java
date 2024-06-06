package org.example.focus.service;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.entity.BookMark;
import org.example.focus.repsitory.BookMarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;

    public List<BookMarkResponseDto> showBookMarkList(Long bookId) {
        List<BookMark> bookMarkList = bookMarkRepository.findAllByIdOrderByDateAsc(bookId);
        return bookMarkList.stream()
                .map(b -> BookMarkResponseDto.from(b))
                .toList();
    }
}
