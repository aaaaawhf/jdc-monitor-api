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
package com.g1335333249.jdc.monitor.api.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.security.dto.OnlineUserDto;
import com.g1335333249.jdc.monitor.api.security.properties.SecurityProperties;
import com.g1335333249.jdc.monitor.api.security.util.EncryptUtils;
import com.g1335333249.jdc.monitor.api.security.util.FileUtil;
import com.g1335333249.jdc.monitor.api.security.util.PageUtil;
import com.g1335333249.jdc.monitor.api.security.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author guanpeng
 * @date 2019年10月26日21:56:27
 */
@Service
@Slf4j
public class OnlineUserService {

    private final SecurityProperties properties;
    private static final Map<String, OnlineUserDto> USERS = new HashMap<>();
    private static final Map<Long, List<String>> USER_ID_TOKENS = new HashMap<>();

    public OnlineUserService(SecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 保存在线用户信息
     *
     * @param systemUser /
     * @param token      /
     * @param request    /
     */
    public void save(SystemUser systemUser, String token, HttpServletRequest request) {
//        String dept = jwtUserDto.getUser().getDept().getName();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(systemUser.getUsername(), systemUser.getUsername(), null, browser, ip, address, EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        USERS.put(properties.getOnlineKey() + token, onlineUserDto);
        List<String> tokens = USER_ID_TOKENS.get(systemUser.getUserId());
        if (CollectionUtils.isEmpty(tokens)) {
            tokens = new ArrayList<>();
            tokens.add(properties.getOnlineKey() + token);
            USER_ID_TOKENS.put(systemUser.getUserId(), tokens);
        } else {
            tokens.add(properties.getOnlineKey() + token);
        }

//        redisUtils.set(properties.getOnlineKey() + token, onlineUserDto, properties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 查询全部数据
     *
     * @param filter /
     * @param page   /
     * @return /
     */
    public Map<String, Object> getAll(String filter, IPage page) {
        List<OnlineUserDto> onlineUserDtos = getAll(filter);
        return PageUtil.toPage(
                PageUtil.toPage(((int) page.getCurrent()), ((int) page.getSize()), onlineUserDtos),
                onlineUserDtos.size()
        );
    }

    /**
     * 查询全部数据，不分页
     *
     * @param filter /
     * @return /
     */
    public List<OnlineUserDto> getAll(String filter) {
//        List<String> keys = redisUtils.scan(properties.getOnlineKey() + "*");
        Set<String> keySet = USERS.keySet();
//        Collections.reverse(keySet);
        List<OnlineUserDto> onlineUserDtos = new ArrayList<>();
        for (String key : keySet) {
            OnlineUserDto onlineUserDto = USERS.get(key);
            if (StringUtils.isNotBlank(filter)) {
                if (onlineUserDto.toString().contains(filter)) {
                    onlineUserDtos.add(onlineUserDto);
                }
            } else {
                onlineUserDtos.add(onlineUserDto);
            }
        }
        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserDtos;
    }

    /**
     * 踢出用户
     *
     * @param key /
     */
    public void kickOut(String key) {
        key = properties.getOnlineKey() + key;
        USERS.remove(key);
    }

    /**
     * 退出登录
     *
     * @param token /
     */
    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        USERS.remove(key);
    }

    /**
     * 删除用户
     *
     * @param userId /
     */
    public void removeAllLoginStatus(Long userId) {
        List<String> tokens = USER_ID_TOKENS.get(userId);
        if (CollectionUtils.isEmpty(tokens)) {
            return;
        }
        tokens.forEach(USERS::remove);
    }

    /**
     * 导出
     *
     * @param all      /
     * @param response /
     * @throws IOException /
     */
    public void download(List<OnlineUserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OnlineUserDto user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", user.getUserName());
            map.put("部门", user.getDept());
            map.put("登录IP", user.getIp());
            map.put("登录地点", user.getAddress());
            map.put("浏览器", user.getBrowser());
            map.put("登录日期", user.getLoginTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 查询用户
     *
     * @param key /
     * @return /
     */
    public OnlineUserDto getOne(String key) {
        return USERS.get(key);
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     *
     * @param userName 用户名
     */
    public void checkLoginOnUser(String userName, String igoreToken) {
        List<OnlineUserDto> onlineUserDtos = getAll(userName);
        if (onlineUserDtos == null || onlineUserDtos.isEmpty()) {
            return;
        }
        for (OnlineUserDto onlineUserDto : onlineUserDtos) {
            if (onlineUserDto.getUserName().equals(userName)) {
                try {
                    String token = EncryptUtils.desDecrypt(onlineUserDto.getKey());
                    if (StringUtils.isNotBlank(igoreToken) && !igoreToken.equals(token)) {
                        this.kickOut(token);
                    } else if (StringUtils.isBlank(igoreToken)) {
                        this.kickOut(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error", e);
                }
            }
        }
    }

}
