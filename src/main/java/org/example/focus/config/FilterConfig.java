package org.example.focus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.example.focus.filter.LoginFilter;
import org.example.focus.filter.SessionIntervalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterConfig {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    @Bean
    public FilterRegistrationBean<Filter> loginFilterRegistrationBean() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new LoginFilter(objectMapper, redisTemplate));
        filter.setOrder(1);
        return filter;
    }

    @Bean
    public FilterRegistrationBean<Filter> sessionIntervalRegistrationBean() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new SessionIntervalFilter());
        filter.setOrder(2);
        return filter;
    }
}
