package com.g1335333249.jdc.monitor.api.model.request;

import lombok.Data;

/**
 * @author guanpeng
 * @description TODO
 * @date 2020/12/22 3:22 下午
 * @since
 */
@Data
public class LoginModel {
    /**
     * 仅在type为公众号或小程序时有效
     */
    private String code;
    private String username;
    private String password;
    /**
     * 小程序 miniapp
     * 公众号 mp
     * 网页 web
     */
    private String type;
}
