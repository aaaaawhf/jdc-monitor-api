package com.g1335333249.jdc.monitor.api.security.handler;

import com.g1335333249.jdc.monitor.api.security.constant.MessageConstant;
import com.g1335333249.jdc.monitor.api.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guanpeng
 * @description 未授权访问
 * @date 2020/4/24 11:33 上午
 * @since
 */
@Component
@Slf4j
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        log.error("403未授权访问");
        ResponseUtil.successToJson(response, HttpStatus.FORBIDDEN.value(), MessageConstant.UNAUTHORIZED);
    }

}
