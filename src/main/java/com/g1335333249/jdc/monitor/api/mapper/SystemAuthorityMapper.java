package com.g1335333249.jdc.monitor.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g1335333249.jdc.monitor.api.entity.SystemAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author guanpeng
 * @since 2020-12-05
 */
public interface SystemAuthorityMapper extends BaseMapper<SystemAuthority> {
    List<SystemAuthority> getAllAuthorities(Long userId);

    @Select(value = "SELECT DISTINCT sa.*,IF (sra.rolel_auth_id IS NULL,0,1) checked FROM system_authority sa LEFT JOIN system_role_authority sra ON sa.auth_id=sra.auth_id AND sra.is_valid=1 AND sra.role_id=#{roleId} WHERE sa.parent_id is null")
    List<SystemAuthority> listByRoleId(@Param("roleId") Long roleId);

    @Select(value = "SELECT DISTINCT sa.*,IF (sra.rolel_auth_id IS NULL,0,1) checked FROM system_authority sa LEFT JOIN system_role_authority sra ON sa.auth_id=sra.auth_id AND sra.is_valid=1 AND sra.role_id=#{roleId} WHERE sa.parent_id = #{authId}")
    List<SystemAuthority> listByAuthIdAndRoleId(@Param("authId") Long authId, @Param("roleId") Long roleId);

}
