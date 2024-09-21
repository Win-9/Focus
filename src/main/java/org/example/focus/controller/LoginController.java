package org.example.focus.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseResponse;
import org.example.focus.dto.request.LoginReqeust;
import org.example.focus.service.MemberService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    @Transactional
    public BaseResponse<Object> postLogin(@RequestBody LoginReqeust reqeust, HttpServletRequest httpServletRequest) {
        memberService.loginMember(reqeust, httpServletRequest);
        return BaseResponse.success();
    }

    @GetMapping("/logout")
    public BaseResponse<Object> logout(HttpServletRequest httpServletRequest) {
        memberService.logoutMember(httpServletRequest);
        return BaseResponse.success();
    }

}
