package com.g1335333249.jdc.monitor.api.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.model.request.AccountDeviceListRequestParam;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
@RestController
@RequestMapping("/account-device-list")
public class AccountDeviceListController {

    @Autowired
    private IAccountDeviceListService iAccountDeviceListService;

    @PostMapping("search")
    public Result<Page<AccountDeviceList>> search(@RequestBody AccountDeviceListRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        LambdaQueryWrapper<AccountDeviceList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AccountDeviceList::getUserId, currentUser.getUserId()).eq(AccountDeviceList::getIsValid, true);
        return Result.success(iAccountDeviceListService.page(new Page<>(request.getPage(), request.getSize()), queryWrapper));
    }

    @PostMapping("updateCost")
    public Result<String> updateCost(@RequestBody AccountDeviceListRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        AccountDeviceList accountDeviceList = iAccountDeviceListService.getById(request.getId());
        if (accountDeviceList == null) {
            return Result.fail("设备不存在，请刷新重试！");
        }
        if (!accountDeviceList.getUserId().equals(currentUser.getUserId())) {
            return Result.fail("非法操作！");
        }
        accountDeviceList.setCost(request.getCost());
        accountDeviceList.setUpdateTime(new Date());
        accountDeviceList.setUpdateUserId(currentUser.getUserId());
        iAccountDeviceListService.saveOrUpdate(accountDeviceList);
        return Result.success();
    }
}
