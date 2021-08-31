package com.g1335333249.jdc.monitor.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g1335333249.jdc.monitor.api.entity.UserNotice;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-17
 */
public interface IUserNoticeService extends IService<UserNotice> {
    void sendNotice(Long userId);

    /**
     * Bark通道
     *
     * @param title
     * @param body
     * @param deviceKey
     */
    void sendByBark(String title, String body, String deviceKey);

    /**
     * server酱通道
     *
     * @param title
     * @param body
     * @param deviceKey
     */
    void sendByServerJ(String title, String body, String deviceKey);
}
