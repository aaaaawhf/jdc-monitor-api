package com.g1335333249.jdc.monitor.api.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.model.Dashboard;

import java.util.Calendar;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
public interface IAccountDeviceListService extends IService<AccountDeviceList> {

    void monitor(AccountDeviceList accountDeviceList, String pin, String tgt, Long updateUserId, Calendar now);

    void updatePoint(Long userAccountId, String tgt, Long updateUserId);

    Page<AccountDeviceList> pageWithSpeed(Page<AccountDeviceList> objectPage, Wrapper<AccountDeviceList> wrapper);

    Dashboard dashboard(Long userId);
}
