package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookMarkModifyRequestdto;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.service.BookMarkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @PostMapping("/bookmark/add")
    public BaseResponse<Object> addBookMark(@RequestPart(value = "request") BookMarkRequestDto request,
                                            @RequestPart(value = "file") MultipartFile file) {
        bookMarkService.processBookMark(request, file);
        return BaseResponse.success();
    }

    @GetMapping("/bookmark/list/{bookId}")
    public BaseResponse<List<AllBookMarkResponseDto>> getBookMarkList(@PathVariable Long bookId) {
        List<AllBookMarkResponseDto> response = bookMarkService.showBookMarkList(bookId);
        return BaseResponse.success(response);
    }

    @GetMapping("/bookmark/{bookMarkId}")
    public BaseResponse<BookMarkResponseDto> getBookMark(@PathVariable Long bookMarkId) {
        BookMarkResponseDto response = bookMarkService.showBookMark(bookMarkId);
        return BaseResponse.success(response);
    }

    @PutMapping("/bookmark/{bookMarkId}")
    public BaseResponse<Object> putBookMark(@PathVariable long bookMarkId, @RequestPart(value = "request") BookMarkModifyRequestdto request,
                                            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        bookMarkService.modifyBookMark(bookMarkId, request, multipartFile);
        return BaseResponse.success();
    }

    @DeleteMapping("/bookmark/removal/{bookMarkId}")
    public BaseResponse<Object> removeBookMark(@PathVariable long bookMarkId) {
        bookMarkService.deleteBookMark(bookMarkId);
        return BaseResponse.success();
    }
}
