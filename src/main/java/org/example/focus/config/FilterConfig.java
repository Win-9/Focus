package org.example.focus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.example.focus.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<Filter> loginFilterRegistrationBean() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new LoginFilter(objectMapper));
        filter.setOrder(1);
        return filter;
    }
}
