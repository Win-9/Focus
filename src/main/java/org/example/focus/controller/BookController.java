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
        CalendarReadInfoResponseDto response = bookService.showCalendarData(year, month);
        return BaseResponse.success(response);
    }

    @PostMapping("/book")
    public BaseResponse<BookResponseDto> addBook(@RequestPart BookCoverRequestDto request,
                                                 @RequestPart MultipartFile file) {
        BookResponseDto response = bookService.processBook(request, file);
        return BaseResponse.success(response);
    }

    @PutMapping("/book/modification/{bookId}")
    public BaseResponse<BookResponseDto> putBook(@PathVariable long bookId,
                                                 @RequestPart BookCoverRequestDto request,
                                                 @RequestPart(required = false) MultipartFile file) {
        BookResponseDto response = bookService.modifyBook(bookId, request, file);
        return BaseResponse.success(response);
    }

    @DeleteMapping("/book/{bookId}")
    public BaseResponse<Object> deleteBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return BaseResponse.success();
    }

    @GetMapping("/book/list")
    public BaseResponse<List<BookListResponseDto>> getBookList() {
        List<BookListResponseDto> response = bookService.showBookList();
        return BaseResponse.success(response);
    }
}
