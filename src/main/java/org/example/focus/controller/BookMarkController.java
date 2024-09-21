package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.Login;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.BookMarkModifyRequestdto;
import org.example.focus.dto.request.BookMarkRequestDto;
import org.example.focus.dto.resopnse.AllBookMarkResponseDto;
import org.example.focus.dto.resopnse.AllBookMarkResponsePageDto;
import org.example.focus.dto.resopnse.BookMarkResponseDto;
import org.example.focus.entity.Member;
import org.example.focus.service.BookMarkService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class BookMarkController {
    private final BookMarkService bookMarkService;

    /**
     * 책갈피 추가
     * @param request
     * @param file
     * @return BookMarkResponseDto
     */
    @PostMapping("/bookmark")
    public ResponseEntity<BookMarkResponseDto> addBookMark(@Validated @RequestPart BookMarkRequestDto request,
                                                           @RequestPart(required = false) MultipartFile file) {
        BookMarkResponseDto response = bookMarkService.processBookMark(request, file);
        return ResponseEntity.ok(response);
    }

    /**
     * 책의 책갈피 전체 조회
     * @param bookId
     * @return List<AllBookMarkResponseDto>
     */
    @GetMapping("/book/{bookId}/bookmarks")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AllBookMarkResponseDto>> getBookMarkList(@Login Member member,
                                                                        @PathVariable String bookId) {
        List<AllBookMarkResponseDto> response = bookMarkService.showBookMarkList(member.getId(), Long.valueOf(bookId));
        return ResponseEntity.ok(response);
    }

    /**
     * 책갈피 전체 조회
     * @param count
     * @param pageable
     * @return AllBookMarkResponsePageDto
     */

    //todo @Login을 통한 전체 bookMark조회
    @GetMapping("/bookmarks")
    @Transactional(readOnly = true)
    public ResponseEntity<AllBookMarkResponsePageDto> getAllBookMarkList(@Login Member member,
                                                                         @RequestParam(required = false) Long count,
                                                                         Pageable pageable) {
        AllBookMarkResponsePageDto response = bookMarkService.showAllBookMarkList(pageable, count);
        return ResponseEntity.ok(response);
    }

    /**
     * 단일 책갈피 조회
     * @param bookMarkId
     * @return BookMarkResponseDto
     */
    @GetMapping("/bookmark/{bookMarkId}")
    @Transactional(readOnly = true)
    public ResponseEntity<BookMarkResponseDto> getBookMark(@PathVariable String bookMarkId) {
        BookMarkResponseDto response = bookMarkService.showBookMark(Long.valueOf(bookMarkId));
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
    public ResponseEntity<BookMarkResponseDto> putBookMark(@PathVariable String bookMarkId, @Validated @RequestPart BookMarkModifyRequestdto request,
                                                           @RequestPart(required = false) MultipartFile file) {
        BookMarkResponseDto response = bookMarkService.modifyBookMark(Long.parseLong(bookMarkId), request, file);
        return ResponseEntity.ok(response);
    }

    /**
     * 책갈피 삭제
     * @param bookMarkId
     * @return BaseResponse
     */
    @DeleteMapping("/bookmark/{bookMarkId}")
    public BaseResponse<Object> removeBookMark(@PathVariable String bookMarkId) {
        bookMarkService.deleteBookMark(Long.parseLong(bookMarkId));
        return BaseResponse.success();
    }
}
