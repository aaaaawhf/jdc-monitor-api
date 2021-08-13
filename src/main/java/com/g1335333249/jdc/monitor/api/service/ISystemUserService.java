package com.g1335333249.jdc.monitor.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guanpeng
 * @since 2020-12-05
 */
public interface ISystemUserService extends IService<SystemUser> {
    SystemUser getWithAuthoritiesByUsername(String username);

    Page<SystemUser> pageJoinDepartment(Page<SystemUser> page, QueryWrapper<SystemUser> query);
}
