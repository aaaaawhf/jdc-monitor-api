package com.g1335333249.jdc.monitor.api.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.entity.UserAccount;
import com.g1335333249.jdc.monitor.api.model.request.UserAccountRequestParam;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.service.IUserAccountService;
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
@RequestMapping("/user-account")
public class UserAccountController {

    @Autowired
    private IUserAccountService iUserAccountService;

    @PostMapping("search")
    public Result<Page<UserAccount>> search(@RequestBody UserAccountRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        LambdaQueryWrapper<UserAccount> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserAccount::getUserId, currentUser.getUserId()).eq(UserAccount::getIsValid, true);
        return Result.success(iUserAccountService.page(new Page<>(request.getPage(), request.getSize()), queryWrapper));
    }

    @PostMapping("create")
    public Result<Page<UserAccount>> create(@RequestBody UserAccountRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(currentUser.getUserId());
        userAccount.setPin(request.getPin());
        userAccount.setTgt(request.getTgt());
        userAccount.setRemark(request.getRemark());
        userAccount.setIsValid(true);
        userAccount.setCreateTime(new Date());
        userAccount.setCreateUserId(currentUser.getUserId());
        // TODO 同步更新设备数量，验证tgt是否有效
        iUserAccountService.save(userAccount);
        return Result.success();
    }

    @PostMapping("update")
    public Result<Page<UserAccount>> update(@RequestBody UserAccountRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        UserAccount userAccount = iUserAccountService.getById(request.getId());
        if (userAccount == null) {
            return Result.fail("账号不存在，请刷新重试！");
        }
        if (!userAccount.getUserId().equals(currentUser.getUserId())) {
            return Result.fail("非法操作！");
        }
        // TODO 同步更新设备数量，验证tgt是否有效
        userAccount.setPin(request.getPin());
        userAccount.setTgt(request.getTgt());
        userAccount.setUpdateUserId(currentUser.getUserId());
        userAccount.setUpdateTime(new Date());
        userAccount.setRemark(request.getRemark());
        iUserAccountService.saveOrUpdate(userAccount);
        return Result.success();
    }

    @PostMapping("delete")
    public Result<Page<UserAccount>> delete(@RequestBody UserAccountRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        UserAccount userAccount = iUserAccountService.getById(request.getId());
        if (userAccount == null) {
            return Result.fail("账号不存在，请刷新重试！");
        }
        if (!userAccount.getUserId().equals(currentUser.getUserId())) {
            return Result.fail("非法操作！");
        }
        userAccount.setIsValid(false);
        userAccount.setUpdateUserId(currentUser.getUserId());
        userAccount.setUpdateTime(new Date());
        iUserAccountService.saveOrUpdate(userAccount);
        return Result.success();
    }
}
