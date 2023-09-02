package com.example.interceptor;

import com.alibaba.fastjson2.JSON;
import com.example.common.utils.JwtUtil;
import com.example.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        log.debug(request.getRequestURI()+" with token:"+token);
        if (token != null){

            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI()+" token is valid.");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI()+" token is invalid.");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(20003, "jwt is invalid,please login again.");
        response.getWriter().write(JSON.toJSONString(fail));
        return false; //拦截

    }
}
