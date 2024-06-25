package org.example.focus.controller;

import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.resopnse.ContinuousReadDateResponse;
import org.example.focus.service.DashBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/continue/count")
    public BaseResponse<ContinuousReadDateResponse> getContinueCount() {
        ContinuousReadDateResponse response = dashBoardService.getCount();
        return BaseResponse.success(response);
    }
}
