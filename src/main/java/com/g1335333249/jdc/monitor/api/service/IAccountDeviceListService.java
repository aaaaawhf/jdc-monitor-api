package com.g1335333249.jdc.monitor.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
public interface IAccountDeviceListService extends IService<AccountDeviceList> {

    void monitor(Long feedId, String pin, String tgt, Long updateUserId);

    void updatePoint(Long userAccountId, String tgt, Long updateUserId);
}
