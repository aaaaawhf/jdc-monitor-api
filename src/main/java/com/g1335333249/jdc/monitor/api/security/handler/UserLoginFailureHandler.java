package com.g1335333249.jdc.monitor.api.security.handler;

import com.g1335333249.jdc.monitor.api.security.constant.MessageConstant;
import com.g1335333249.jdc.monitor.api.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guanpeng
 * @description 登录失败处理
 * @date 2020/4/24 11:14 上午
 * @since
 */
@Component
@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("登录失败");
        String msg;
        if (e instanceof LockedException) {
            msg = MessageConstant.LOCKED_EXCEPTION;
        } else if (e instanceof CredentialsExpiredException) {
            msg = MessageConstant.CREDENTIALS_EXPIRED;
        } else if (e instanceof AccountExpiredException) {
            msg = MessageConstant.ACCOUNT_EXPIRED;
        } else if (e instanceof DisabledException) {
            msg = MessageConstant.DISABLED;
        } else if (e instanceof BadCredentialsException) {
            msg = MessageConstant.BAD_CREDENTIALS;
        } else {
            msg = e.getMessage();
        }
        ResponseUtil.failToJson(response, msg);
    }
}
