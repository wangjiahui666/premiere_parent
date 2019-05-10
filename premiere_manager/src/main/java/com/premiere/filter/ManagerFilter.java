package com.premiere.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.premiere.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public ManagerFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String filterType() {
        return "pre";//前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;//优先级，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;//过滤器开关，true表示开启
    }

    @Override
    public Object run() {
        System.out.println("Zuul过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        String url = request.getRequestURL().toString();
        if (url.indexOf("/admin/login") > 0) {
            System.out.println("登陆页面" + url);
            return null;
        }
        String authHeader = request.getHeader("Authorization");
        if (null != authHeader && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (null != claims) {
                if ("admin".equals(claims.get("roles"))) {
                    requestContext.addZuulRequestHeader("Authorization", authHeader);
                    System.out.println("token 验证通过，添加了头信 息" + authHeader);
                }
            }
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
