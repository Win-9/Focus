package org.example.focus.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseErrorResopnse;
import org.example.focus.config.SessionConst;
import org.example.focus.exception.ErrorCode;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@AllArgsConstructor
public class LoginFilter implements Filter {
    private static final String[] whitelist = {"/api/login", "/api/logout"};
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (checkWhitelist(requestURI)) {
            HttpSession session = request.getSession();
            if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(objectMapper.writeValueAsString(BaseErrorResopnse.of(ErrorCode.LOGIN_EXCPETION)));
                return;
            }
        }
        filterChain.doFilter(request, servletResponse);
    }

    private static boolean checkWhitelist(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
