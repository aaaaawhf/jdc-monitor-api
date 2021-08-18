package com.g1335333249.jdc.monitor.api.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceListSpeedMonitor;
import com.g1335333249.jdc.monitor.api.model.request.AccountDeviceListSpeedMonitorRequestParam;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListSpeedMonitorService;
import com.g1335333249.jdc.monitor.api.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-16
 */
@RestController
@RequestMapping("/account-device-list-speed-monitor")
public class AccountDeviceListSpeedMonitorController {

    @Autowired
    private IAccountDeviceListSpeedMonitorService iAccountDeviceListSpeedMonitorService;

    @PostMapping("search")
    public Result<List<AccountDeviceListSpeedMonitor>> search(@RequestBody AccountDeviceListSpeedMonitorRequestParam request) {
//        Calendar instance = Calendar.getInstance();
//        instance.set(Calendar.HOUR, 0);
//        instance.set(Calendar.MINUTE, 0);
//        instance.set(Calendar.SECOND, 0);
        LambdaQueryWrapper<AccountDeviceListSpeedMonitor> query = Wrappers.lambdaQuery();
        query.eq(AccountDeviceListSpeedMonitor::getAccountDeviceListId, request.getAccountDeviceListId());
        query.apply("to_days(" + AccountDeviceListSpeedMonitor.CREATE_TIME + ") = to_days(now())");
//        query.between(AccountDeviceListSpeedMonitor::getCreateTime, instance.getTime(), new Date());
        query.orderByAsc(AccountDeviceListSpeedMonitor::getCreateTime);
        return Result.success(iAccountDeviceListSpeedMonitorService.list(query));
    }
}
