package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.dto.resopnse.BookListResponseDto;
import org.example.focus.dto.resopnse.CalendarReadInfoResponseDto;
import org.example.focus.service.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/calendar/{year}/{month}")
    public BaseResponse<CalendarReadInfoResponseDto> getCalendar(@PathVariable int year, @PathVariable int month) {
        return bookService.showCalendarData(year, month);
    }

    @PostMapping("/book")
    public BaseResponse<Object> addBook(@RequestPart(value = "request") BookCoverRequestDto bookCoverRequestDto,
                                        @RequestPart(value = "file") MultipartFile multipartFile) {
        bookService.processBook(bookCoverRequestDto, multipartFile);
        return BaseResponse.success();
    }

    @GetMapping("/book/list")
    public BaseResponse<List<BookListResponseDto>> getBookList() {
        return bookService.showBookList();
    }
}
