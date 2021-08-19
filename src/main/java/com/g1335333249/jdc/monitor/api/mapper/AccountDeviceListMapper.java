package com.g1335333249.jdc.monitor.api.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.model.Dashboard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
public interface AccountDeviceListMapper extends BaseMapper<AccountDeviceList> {

    Page<AccountDeviceList> pageWithSpeed(Page<AccountDeviceList> page, @Param(Constants.WRAPPER) Wrapper<AccountDeviceList> wrapper);

    Dashboard dashboard(@Param("userId") Long userId);
}
