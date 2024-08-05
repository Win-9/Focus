package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookCoverRequestDto;
import org.example.focus.dto.resopnse.BookListResponseDto;
import org.example.focus.dto.resopnse.BookResponseDto;
import org.example.focus.dto.resopnse.CalendarReadInfoResponseDto;
import org.example.focus.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * 년, 월별 독서량 조회
     * @param year
     * @param month
     * @return CalendarReadInfoResponseDto
     */
    @GetMapping("/calendar/{year}/{month}")
    public ResponseEntity<CalendarReadInfoResponseDto> getCalendar(@PathVariable int year, @PathVariable int month) {
        CalendarReadInfoResponseDto response = bookService.showCalendarData(year, month);
        return ResponseEntity.ok(response);
    }

    /**
     * 책 단일 조회
     * @param bookId
     * @return
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable long bookId) {
        BookResponseDto response = bookService.showBook(bookId);
        return ResponseEntity.ok(response);
    }

    /**
     * 책 추가
     * @param request
     * @param file
     * @return BookResponseDto
     */
    @PostMapping("/book")
    public ResponseEntity<BookResponseDto> addBook(@RequestPart BookCoverRequestDto request,
                                                   @RequestPart(required = false) MultipartFile file) {
        BookResponseDto response = bookService.processBook(request, file);
        return ResponseEntity.ok(response);
    }

    /**
     * 책 수정
     * @param bookId
     * @param request
     * @param file
     * @return BookResponseDto
     */
    @PutMapping("/book/{bookId}")
    public ResponseEntity<BookResponseDto> putBook(@PathVariable long bookId,
                                                   @RequestPart BookCoverRequestDto request,
                                                   @RequestPart(required = false) MultipartFile file) {
        BookResponseDto response = bookService.modifyBook(bookId, request, file);
        return ResponseEntity.ok(response);
    }

    /**
     * 책 삭제
     * @param bookId
     * @return BaseResponse
     */
    @DeleteMapping("/book/{bookId}")
    public BaseResponse<Object> deleteBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return BaseResponse.success();
    }

    /**
     * 책 전체 조회
     * @return BookListResponseDto
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookListResponseDto>> getBookList() {
        List<BookListResponseDto> response = bookService.showBookList();
        return ResponseEntity.ok(response);
    }
}
