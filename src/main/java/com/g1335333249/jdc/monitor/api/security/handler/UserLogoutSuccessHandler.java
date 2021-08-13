package com.g1335333249.jdc.monitor.api.security.handler;

import com.g1335333249.jdc.monitor.api.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guanpeng
 * @description 注销成功处理
 * @date 2020/4/24 11:55 上午
 * @since
 */
@Component
@Slf4j
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            log.info("用户【{}】注销成功", authentication.getName());
            ResponseUtil.failToJson(response, authentication);
        }
    }
}
