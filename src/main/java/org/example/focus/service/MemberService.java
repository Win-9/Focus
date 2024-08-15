package org.example.focus.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.focus.config.SessionConst;
import org.example.focus.dto.request.LoginReqeust;
import org.example.focus.entity.Member;
import org.example.focus.exception.ErrorCode;
import org.example.focus.exception.notexist.MemberNotExistException;
import org.example.focus.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    
    public void loginMember(LoginReqeust reqeust, HttpServletRequest servletRequest) {
        Member member = memberRepository.findByMemberId(reqeust.getId());
        if (!member.getPassword().equals(reqeust.getPassword())) {
            throw new MemberNotExistException(ErrorCode.MEMBER_NOT_EXIST);
        }

        servletRequest.getSession().invalidate();
        HttpSession session = servletRequest.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER, reqeust.getId());
        session.setMaxInactiveInterval(1800);
    }
}
