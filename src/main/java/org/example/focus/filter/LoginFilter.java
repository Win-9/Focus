package org.example.focus.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.focus.common.BaseErrorResopnse;
import org.example.focus.config.SessionConst;
import org.example.focus.exception.ErrorCode;
import org.example.focus.util.EncryptUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter implements Filter {
    private static final String[] whitelist = {"/api/login", "/api/logout"};
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (checkWhitelist(requestURI)) {
            HttpSession session = request.getSession();
            if (session == null) {
                responseError(response);
                return;
            }

            String sessionId = session.getId();
            if (!redisTemplate.hasKey(EncryptUtil.namespace + SessionConst.REDIS_SESSION_KEY + sessionId)) {
                responseError(response);
                return;
            }
        }
        filterChain.doFilter(request, servletResponse);
    }

    private void responseError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(BaseErrorResopnse.of(ErrorCode.LOGIN_EXCPETION)));
    }

    private static boolean checkWhitelist(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
