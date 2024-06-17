package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.dto.resopnse.BookListResponseDto;
import org.example.focus.dto.resopnse.BookResponseDto;
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
    public BaseResponse<BookResponseDto> addBook(@RequestPart(value = "request") BookCoverRequestDto bookCoverRequestDto,
                                                 @RequestPart(value = "file") MultipartFile multipartFile) {
        return bookService.processBook(bookCoverRequestDto, multipartFile);
    }

    @PutMapping("/book/{bookId}")
    public BaseResponse<BookResponseDto> putBook(@PathVariable long bookId,
                                                 @RequestPart(value = "request") BookCoverRequestDto requestDto,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) {
        return bookService.modifyBook(bookId, requestDto, file);
    }

    @DeleteMapping("/book/{bookId}")
    public BaseResponse<Object> deleteBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return BaseResponse.success();
    }

    @GetMapping("/book/list")
    public BaseResponse<List<BookListResponseDto>> getBookList() {
        return bookService.showBookList();
    }
}
