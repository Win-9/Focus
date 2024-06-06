package org.example.focus.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.service.BookMarkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @GetMapping("/bookmark/{bookId}")
    public BaseResponse<List<BookMarkResponseDto>> getBookMarkList(@PathVariable Long bookId) {
        List<BookMarkResponseDto> response = bookMarkService.showBookMarkList(bookId);
        return BaseResponse.success(response);
    }

    @GetMapping("/bookmark/{bookId}/{bookMarkId}")
    public void getBookMark(@PathVariable Long bookId) {
        bookMarkService.showBookMark(bookId);
    }
}
