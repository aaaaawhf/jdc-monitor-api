package com.g1335333249.jdc.monitor.api.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author guanpeng
 * @since 2020-12-05
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    SystemUser getWithAuthoritiesByUsername(String username);

    @Select(value = "SELECT su.*,sd.department_name FROM system_user su LEFT JOIN system_department sd ON su.department_id = sd.department_id ${ew.customSqlSegment}")
    Page<SystemUser> pageJoinDepartment(Page<SystemUser> page, @Param(Constants.WRAPPER) QueryWrapper<SystemUser> query);
}
