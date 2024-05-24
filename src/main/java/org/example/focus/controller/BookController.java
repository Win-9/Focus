package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.resopnse.CalendarReadInfoResponseDto;
import org.example.focus.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/calendar/{year}/{month}")
    public BaseResponse<CalendarReadInfoResponseDto> getCalendar(@PathVariable int year, @PathVariable int month) {
        return bookService.showCalendarData(year, month);
    }
}
