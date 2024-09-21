package org.example.focus.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class SessionIntervalFilter implements Filter {
    private static final String[] whitelist = {"/api/login", "/api/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        if (checkWhitelist(requestURI) && request.getSession(false) != null) {
            request.getSession().setMaxInactiveInterval(1800);
        }
    }

    private boolean checkWhitelist(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
