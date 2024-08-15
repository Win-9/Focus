package org.example.focus.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.LoginReqeust;
import org.example.focus.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public BaseResponse<Object> postLogin(@RequestBody LoginReqeust reqeust, HttpServletRequest servletRequest) {
        memberService.loginMember(reqeust, servletRequest);
        return BaseResponse.success();
    }
}
