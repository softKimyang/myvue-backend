package com.example.vuebackboard.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

public class WebMvcConfig {
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver = new SortHandlerMethodArgumentResolver();
        sortHandlerMethodArgumentResolver.setSortParameter("sortBy");
        sortHandlerMethodArgumentResolver.setPropertyDelimiter("-");

        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();
        pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
        pageableHandlerMethodArgumentResolver.setMaxPageSize(500);
        pageableHandlerMethodArgumentResolver.setFallbackPageable(PageRequest.of(0, 10));
        argumentResolvers.add(pageableHandlerMethodArgumentResolver);
    }
}
