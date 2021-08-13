package com.g1335333249.jdc.monitor.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.mapper.SystemUserMapper;
import com.g1335333249.jdc.monitor.api.service.ISystemUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guanpeng
 * @since 2020-12-05
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {
    @Override
    public SystemUser getWithAuthoritiesByUsername(String username) {
        return getBaseMapper().getWithAuthoritiesByUsername(username);
    }

    @Override
    public Page<SystemUser> pageJoinDepartment(Page<SystemUser> page, QueryWrapper<SystemUser> query) {
        return getBaseMapper().pageJoinDepartment(page, query);
    }

}
