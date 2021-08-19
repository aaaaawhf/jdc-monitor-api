package com.g1335333249.jdc.monitor.api.controller;

import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.model.Dashboard;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListSpeedMonitorService;
import com.g1335333249.jdc.monitor.api.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/19 10:15
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("dashboard")
public class DashboardController {
    @Autowired
    private IAccountDeviceListService iAccountDeviceListService;
    @Autowired
    private IAccountDeviceListSpeedMonitorService iAccountDeviceListSpeedMonitorService;

    @PostMapping("index")
    public Result<Dashboard> index() {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        return Result.success(iAccountDeviceListService.dashboard(currentUser.getUserId()));
    }
}
