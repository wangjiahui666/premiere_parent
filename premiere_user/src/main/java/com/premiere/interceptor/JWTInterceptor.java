package com.premiere.interceptor;

import com.premiere.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JWTInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader("Authorization");
        //判断 header中的数据不为空
        if (StringUtils.isEmpty(header)) {
            throw new RuntimeException("权限不足");
        }

        //判断 Bearer 也为null header.startsWith() 判断字符串以什么开头的数据是否存在
        if (!header.startsWith("Bearer ")) {
            throw new RuntimeException("权限不足");
        }
        //截取出  token
        String token = header.substring(7);
        Claims claims = jwtUtil.parseJWT(token);
        //效验 claims 不为空
        if (null == claims) {
            throw new RuntimeException("权限不足");
        }
        if (StringUtils.equals("admin", (String) claims.get("roles"))){
            request.setAttribute("admin_claims", claims);
        }else if (StringUtils.equals("user", (String) claims.get("roles"))){
            request.setAttribute("user_claims", claims);
        }

        return true;
    }
}
