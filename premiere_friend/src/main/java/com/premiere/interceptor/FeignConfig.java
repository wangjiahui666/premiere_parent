package com.premiere.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig implements RequestInterceptor {

    private final HttpServletRequest request;

    @Autowired
    public FeignConfig(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void apply(RequestTemplate template) {
        String authorization = request.getHeader("Authorization");
        if (null==authorization){
            throw new RuntimeException("请先登录");
        }
        template.header("Authorization",authorization);
    }
}
