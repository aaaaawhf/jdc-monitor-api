package com.g1335333249.jdc.monitor.api.security.handler;

import com.g1335333249.jdc.monitor.api.security.constant.MessageConstant;
import com.g1335333249.jdc.monitor.api.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guanpeng
 * @description TODO
 * @date 2020/4/24 11:47 上午
 * @since
 */
@Component
@Slf4j
public class UserLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("【{}】未登录", request.getRequestURI());
        ResponseUtil.failToJson(response, HttpStatus.UNAUTHORIZED.value(), MessageConstant.LOGIN_TIMEOUT);
    }
}
