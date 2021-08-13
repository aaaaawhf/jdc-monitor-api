/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.g1335333249.jdc.monitor.api.security.filter;

import com.g1335333249.jdc.monitor.api.entity.VisitLog;
import com.g1335333249.jdc.monitor.api.security.config.SpringContextHolder;
import com.g1335333249.jdc.monitor.api.security.dto.OnlineUserDto;
import com.g1335333249.jdc.monitor.api.security.properties.SecurityProperties;
import com.g1335333249.jdc.monitor.api.security.service.OnlineUserService;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.security.util.StringUtils;
import com.g1335333249.jdc.monitor.api.service.IVisitLogService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author /
 */
@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;
    private final IVisitLogService iVisitLogService;

    private static final Map<String, String> URL = new HashMap<String, String>() {{
        put("/customer/search", "客户列表");
        put("/customer/update", "更新客户");
        put("/customer/delete", "删除客户");
        put("/customer/updateDate", "同步客户数据");
        put("/sale-order/search", "订单列表");
        put("/sale-order-info/listBySaleOrderId", "订单详情");
        put("/sale-order/updateDate", "同步订单数据");
        put("/goods/search", "商品列表");
        put("/goods/updateDate", "同步商品数据");
        put("/goods-tag/search", "商品标签列表");
        put("/goods-tag/updateSortOrder", "更改标签排序");
        put("/goods-tag-relation/update", "商品绑定标签");
        put("/goods-tag/save", "新增商品标签");
        put("/goods-tag/delete", "删除商品标签");
        put("/saleOrderStatistics/listSaleOrder", "获取订单统计");
        put("/saleOrderStatistics/download", "下载订单统计");
        put("/system-user/search", "用户列表");
        put("/system-role/search", "角色列表");
        put("/system-user/save", "新增用户");
        put("/system-role/listByUserId", "查询用户角色");
        put("/system-role/updateRoleByUserId", "更新用户角色");
        put("/system-user/delete", "删除用户");
        put("/auth/logout", "用户注销");
        put("/auth/login", "用户登录");
    }};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);
        String requestRri = httpServletRequest.getRequestURI();
        // 验证 token 是否存在
        OnlineUserDto onlineUserDto = null;
        try {
            SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
            OnlineUserService onlineUserService = SpringContextHolder.getBean(OnlineUserService.class);
            onlineUserDto = onlineUserService.getOne(properties.getOnlineKey() + token);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        }
        if (onlineUserDto != null && StringUtils.isNoneBlank(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestRri);
        } else {
            log.debug("no valid JWT token found, uri: {}", requestRri);
        }
        try {
            iVisitLogService.save(new VisitLog().setRemark(URL.get(httpServletRequest.getRequestURI())).setVisitDate(new Date()).setVisitUri(httpServletRequest.getRequestURI()).setVisitUsername(SecurityUtils.getCurrentUsername())
                    .setVisitIp(StringUtils.getIp(httpServletRequest))
                    .setVisitAddress(StringUtils.getCityInfo(StringUtils.getIp(httpServletRequest)))
                    .setVisitBrowser(StringUtils.getBrowser(httpServletRequest))
            );
        } catch (Exception ignored) {
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        }
        return null;
    }
}
