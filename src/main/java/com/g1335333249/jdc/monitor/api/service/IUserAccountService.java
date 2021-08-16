package com.g1335333249.jdc.monitor.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g1335333249.jdc.monitor.api.entity.UserAccount;
import com.g1335333249.jdc.monitor.api.model.jdc.AppDeviceResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
public interface IUserAccountService extends IService<UserAccount> {
    /**
     * 更新设备列表
     *
     * @param appDeviceResults
     * @param updateUserId
     * @param userAccount
     */
    void updateAccountDeviceList(List<AppDeviceResult> appDeviceResults, Long updateUserId, UserAccount userAccount);
}
