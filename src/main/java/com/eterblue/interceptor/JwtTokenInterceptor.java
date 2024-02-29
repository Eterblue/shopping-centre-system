package com.eterblue.interceptor;

import com.eterblue.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //获取token
        String token = request.getHeader("token");
        log.info("jwt校验:{}",token);
        if(token == null){
            log.info("token为空，不放行");
            return false;
        }
        try {
            log.info("用户信息:{}",JwtUtil.decodeUser(token));
        } catch (Exception e) {
            log.info("token失效");
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
