package com.g1335333249.jdc.monitor.api.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.entity.UserAccount;
import com.g1335333249.jdc.monitor.api.model.jdc.AppDeviceInfo;
import com.g1335333249.jdc.monitor.api.model.jdc.AppDeviceResult;
import com.g1335333249.jdc.monitor.api.model.request.UserAccountRequestParam;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.IUserAccountService;
import com.g1335333249.jdc.monitor.api.service.JdcService;
import com.g1335333249.jdc.monitor.api.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
    @Autowired
    private IAccountDeviceListService iAccountDeviceListService;
    @Autowired
    private JdcService jdcService;

    @PostMapping("search")
    public Result<Page<UserAccount>> search(@RequestBody UserAccountRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        LambdaQueryWrapper<UserAccount> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserAccount::getUserId, currentUser.getUserId()).eq(UserAccount::getIsValid, true);
        return Result.success(iUserAccountService.page(new Page<>(request.getPage(), request.getSize()), queryWrapper));
    }

    @PostMapping("create")
    public Result<Page<UserAccount>> create(@RequestBody UserAccountRequestParam request) {
        List<AppDeviceResult> appDeviceResults = jdcService.listAllUserDevices(request.getPin(), request.getTgt());
        if (CollectionUtils.isEmpty(appDeviceResults)) {
            return Result.fail("请检查pin和tgt是否正确，及此账户是否有设备");
        }
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(currentUser.getUserId());
        userAccount.setPin(request.getPin());
        userAccount.setTgt(request.getTgt());
        userAccount.setDeviceCount(appDeviceResults.get(0).getCount());
        userAccount.setRemark(request.getRemark());
        userAccount.setIsValid(true);
        userAccount.setCreateTime(new Date());
        userAccount.setCreateUserId(currentUser.getUserId());
        iUserAccountService.save(userAccount);
        updateAccountDeviceList(appDeviceResults, currentUser, userAccount);

        return Result.success();
    }

    private void updateAccountDeviceList(List<AppDeviceResult> appDeviceResults, SystemUser currentUser, UserAccount userAccount) {
        CompletableFuture.runAsync(() -> {
            List<AppDeviceInfo> appDeviceInfos = appDeviceResults.get(0).getList().stream().filter(s -> "213400001".equals(s.getProductId()) || "169500020".equals(s.getProductId())).collect(Collectors.toList());

            for (AppDeviceInfo appDeviceInfo : appDeviceInfos) {
                AccountDeviceList accountDeviceList;
                List<AccountDeviceList> accountDeviceLists = iAccountDeviceListService.list(Wrappers.<AccountDeviceList>lambdaQuery().eq(AccountDeviceList::getFeedId, appDeviceInfo.getFeedId()).eq(AccountDeviceList::getUserId, currentUser.getUserId()).eq(AccountDeviceList::getUserAccountId, userAccount.getId()));
                if (CollectionUtils.isEmpty(accountDeviceLists)) {
                    accountDeviceList = new AccountDeviceList();
                    accountDeviceList.setUserId(currentUser.getUserId());
                    accountDeviceList.setUserAccountId(userAccount.getId());
                    accountDeviceList.setProductId(appDeviceInfo.getProductId());
                    accountDeviceList.setDeviceId(appDeviceInfo.getDeviceId());
                    accountDeviceList.setStatus(appDeviceInfo.getStatus());
                    accountDeviceList.setConfigType(appDeviceInfo.getConfigType());
                    accountDeviceList.setDeviceType(appDeviceInfo.getDeviceType());
                    accountDeviceList.setCid(appDeviceInfo.getCid());
                    accountDeviceList.setPImgUrl(appDeviceInfo.getPImgUrl());
                    accountDeviceList.setDeviceName(appDeviceInfo.getDeviceName());
                    accountDeviceList.setFeedId(appDeviceInfo.getFeedId());
                    accountDeviceList.setVersion(appDeviceInfo.getVersion());
                    accountDeviceList.setOwnFlag(appDeviceInfo.getOwnFlag());
                    accountDeviceList.setDeviceAddTime(new Date(appDeviceInfo.getCreateTime()));
                    accountDeviceList.setAccessKey(appDeviceInfo.getAccessKey());
                    accountDeviceList.setDevicePageType(appDeviceInfo.getDevicePageType());
                    accountDeviceList.setCname(appDeviceInfo.getCname());
                    accountDeviceList.setIsValid(true);
                    accountDeviceList.setCreateTime(new Date());
                    accountDeviceList.setCreateUserId(currentUser.getUserId());
                } else {
                    accountDeviceList = accountDeviceLists.get(0);
                    accountDeviceList.setProductId(appDeviceInfo.getProductId());
                    accountDeviceList.setDeviceId(appDeviceInfo.getDeviceId());
                    accountDeviceList.setStatus(appDeviceInfo.getStatus());
                    accountDeviceList.setConfigType(appDeviceInfo.getConfigType());
                    accountDeviceList.setDeviceType(appDeviceInfo.getDeviceType());
                    accountDeviceList.setCid(appDeviceInfo.getCid());
                    accountDeviceList.setPImgUrl(appDeviceInfo.getPImgUrl());
                    accountDeviceList.setDeviceName(appDeviceInfo.getDeviceName());
                    accountDeviceList.setFeedId(appDeviceInfo.getFeedId());
                    accountDeviceList.setVersion(appDeviceInfo.getVersion());
                    accountDeviceList.setOwnFlag(appDeviceInfo.getOwnFlag());
                    accountDeviceList.setDeviceAddTime(new Date(appDeviceInfo.getCreateTime()));
                    accountDeviceList.setAccessKey(appDeviceInfo.getAccessKey());
                    accountDeviceList.setDevicePageType(appDeviceInfo.getDevicePageType());
                    accountDeviceList.setCname(appDeviceInfo.getCname());
                    accountDeviceList.setUpdateTime(new Date());
                    accountDeviceList.setUpdateUserId(currentUser.getUserId());
                }
                iAccountDeviceListService.saveOrUpdate(accountDeviceList);
            }
        });
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
        List<AppDeviceResult> appDeviceResults = jdcService.listAllUserDevices(request.getPin(), request.getTgt());
        if (CollectionUtils.isEmpty(appDeviceResults)) {
            return Result.fail("请检查pin和tgt是否正确，及此账户是否有设备");
        }
        userAccount.setPin(request.getPin());
        userAccount.setTgt(request.getTgt());
        userAccount.setUpdateUserId(currentUser.getUserId());
        userAccount.setUpdateTime(new Date());
        userAccount.setRemark(request.getRemark());
        iUserAccountService.saveOrUpdate(userAccount);

        updateAccountDeviceList(appDeviceResults, currentUser, userAccount);
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
