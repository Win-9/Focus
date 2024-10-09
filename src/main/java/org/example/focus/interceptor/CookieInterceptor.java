package org.example.focus.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.focus.config.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CookieInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 세션 쿠키 가져오기
        Cookie sessionCookie = new Cookie(SessionConst.LOGIN_USER, String.valueOf(request.getSession().getAttribute(SessionConst.LOGIN_USER)));
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(false);
        sessionCookie.setSecure(false);

        // SameSite 속성 수동 설정
        response.addHeader("Set-Cookie", sessionCookie.getName() + "=" + sessionCookie.getValue() +
                "; Path=" + sessionCookie.getPath() +
                "; HttpOnly=" + (sessionCookie.isHttpOnly() ? "true" : "false") +
                "; Secure=" + (sessionCookie.getSecure() ? "true" : "false") +
                "; SameSite=None");
    }
}
