package com.g1335333249.jdc.monitor.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.entity.UserNotice;
import com.g1335333249.jdc.monitor.api.model.request.UserNoticeRequestParam;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.service.IUserNoticeService;
import com.g1335333249.jdc.monitor.api.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/user-notice")
public class UserNoticeController {
    @Autowired
    private IUserNoticeService iUserNoticeService;

    @PostMapping("search")
    public Result<UserNotice> search(@RequestBody UserNoticeRequestParam request) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        LambdaQueryWrapper<UserNotice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserNotice::getUserId, currentUser.getUserId()).eq(UserNotice::getIsValid, true);
        List<UserNotice> list = iUserNoticeService.list(queryWrapper);
        return Result.success(CollectionUtils.isEmpty(list) ? new UserNotice() : list.get(0));
    }

    @PostMapping("update")
    public Result<String> update(@RequestBody UserNotice userNotice) {
        SystemUser currentUser = SecurityUtils.getCurrentUser();
        if (userNotice.getId() == null) {
            UserNotice temp = new UserNotice();
            temp.setNoticeType(userNotice.getNoticeType());
            temp.setIsValid(true);
            temp.setIsEnabled(userNotice.getIsEnabled());
            temp.setUserId(currentUser.getUserId());
            temp.setCreateUserId(currentUser.getUserId());
            temp.setCreateTime(new Date());
            temp.setConfigJson(userNotice.getConfigJson());
            iUserNoticeService.save(temp);
        } else {
            UserNotice temp = iUserNoticeService.getById(userNotice.getId());
            if (temp == null) {
                return Result.fail("通知不存在，请刷新重试！");
            }
            if (!temp.getUserId().equals(currentUser.getUserId())) {
                return Result.fail("非法操作！");
            }
            temp.setNoticeType(userNotice.getNoticeType());
            temp.setIsEnabled(userNotice.getIsEnabled());
            temp.setConfigJson(userNotice.getConfigJson());
            temp.setUpdateTime(new Date());
            temp.setUpdateUserId(currentUser.getUserId());
            iUserNoticeService.saveOrUpdate(temp);
        }
        return Result.success();
    }

}
