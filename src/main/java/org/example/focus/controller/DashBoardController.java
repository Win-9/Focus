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

    @GetMapping("/continue/count")
    public ResponseEntity<ContinuousReadDateResponse> getContinueCount() {
        ContinuousReadDateResponse response = dashBoardService.getCount();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookmark/count")
    public ResponseEntity<BookMarkCountResponse> getBookMarkCount() {
        BookMarkCountResponse response = dashBoardService.getBookMarkCount();
        return ResponseEntity.ok(response);
    }
}
