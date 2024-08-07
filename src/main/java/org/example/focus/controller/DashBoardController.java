package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.dto.resopnse.BookMarkCountResponse;
import org.example.focus.dto.resopnse.ContinuousReadDateResponse;
import org.example.focus.service.DashBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashBoardService dashBoardService;

    /**
     * 연속일 조회
     * @return ContinuousReadDateResponse
     */
    @GetMapping("/count/continuity")
    public ResponseEntity<ContinuousReadDateResponse> getContinueCount() {
        ContinuousReadDateResponse response = dashBoardService.getCount();
        return ResponseEntity.ok(response);
    }

    /**
     * 증가량 조회
     * @return BookMarkCountResponse
     */
    @GetMapping("/count/bookmarks")
    public ResponseEntity<BookMarkCountResponse> getBookMarkCount() {
        BookMarkCountResponse response = dashBoardService.getBookMarkCount();
        return ResponseEntity.ok(response);
    }
}
