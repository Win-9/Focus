package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookMarkModifyRequestdto;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.AllBookMarkResponsePageDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.service.BookMarkService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    /**
     * 책갈피 추가
     * @param request
     * @param file
     * @return BookMarkResponseDto
     */
    @PostMapping("/bookmark")
    public ResponseEntity<BookMarkResponseDto> addBookMark(@RequestPart BookMarkRequestDto request,
                                                           @RequestPart(required = false) MultipartFile file) {
        BookMarkResponseDto response = bookMarkService.processBookMark(request, file);
        return ResponseEntity.ok(response);
    }

    /**
     * 책의 책갈피 전체 조회
     * @param bookId
     * @return List<AllBookMarkResponseDto>
     */
    @GetMapping("/bookmark/list/{bookId}")
    public ResponseEntity<List<AllBookMarkResponseDto>> getBookMarkList(@PathVariable Long bookId) {
        List<AllBookMarkResponseDto> response = bookMarkService.showBookMarkList(bookId);
        return ResponseEntity.ok(response);
    }

    /**
     * 책갈피 전체 조회
     * @param count
     * @param pageable
     * @return AllBookMarkResponsePageDto
     */
    @GetMapping("/bookmark/list")
    public ResponseEntity<AllBookMarkResponsePageDto> getAllBookMarkList(@RequestParam(required = false) Long count, Pageable pageable) {
        AllBookMarkResponsePageDto response = bookMarkService.showAllBookMarkList(pageable, count);
        return ResponseEntity.ok(response);
    }

    /**
     * 단일 책갈피 조회
     * @param bookMarkId
     * @return BookMarkResponseDto
     */
    @GetMapping("/bookmark/{bookMarkId}")
    public ResponseEntity<BookMarkResponseDto> getBookMark(@PathVariable Long bookMarkId) {
        BookMarkResponseDto response = bookMarkService.showBookMark(bookMarkId);
        return ResponseEntity.ok(response);
    }

    /**
     * 책갈피 수정
     * @param bookMarkId
     * @param request
     * @param file
     * @return BookMarkResponseDto
     */
    @PutMapping("/bookmark/{bookMarkId}")
    public ResponseEntity<BookMarkResponseDto> putBookMark(@PathVariable long bookMarkId, @RequestPart BookMarkModifyRequestdto request,
                                                           @RequestPart(required = false) MultipartFile file) {
        BookMarkResponseDto response = bookMarkService.modifyBookMark(bookMarkId, request, file);
        return ResponseEntity.ok(response);
    }

    /**
     * 책갈피 삭제
     * @param bookMarkId
     * @return BaseResponse
     */
    @DeleteMapping("/bookmark/{bookMarkId}")
    public BaseResponse<Object> removeBookMark(@PathVariable long bookMarkId) {
        bookMarkService.deleteBookMark(bookMarkId);
        return BaseResponse.success();
    }
}
