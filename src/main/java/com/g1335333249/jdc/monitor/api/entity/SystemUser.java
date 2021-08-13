package com.g1335333249.jdc.monitor.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author guanpeng
 * @since 2020-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user")
public class SystemUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 昵称
     */
    @TableField("name")
    private String name;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 账户是否过期
     */
    @Getter(value = AccessLevel.NONE)
    @TableField("account_non_expired")
    private Boolean accountNonExpired;

    /**
     * 账户是否锁定
     */
    @Getter(value = AccessLevel.NONE)
    @TableField("account_non_locked")
    private Boolean accountNonLocked;

    /**
     * 登录凭证是否过期
     */
    @Getter(value = AccessLevel.NONE)
    @TableField("credentials_non_expired")
    private Boolean credentialsNonExpired;

    /**
     * 账户是否启用
     */
    @Getter(value = AccessLevel.NONE)
    @TableField("enabled")
    private Boolean enabled;

    /**
     * 是否有效
     */
    @TableField("is_valid")
    private Boolean isValid;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Long createId;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 修改人ID
     */
    @TableField(value = "update_id", fill = FieldFill.UPDATE)
    private Long updateId;

    @TableField(exist = false)
    private List<SystemAuthority> authorities;

    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 部门名称
     */
    @TableField(value = "department_name",exist = false)
    private String departmentName;

    public static final String USER_ID = "user_id";

    public static final String DEPARTMENT_ID = "department_id";

    public static final String NAME = "name";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String EMAIL = "email";

    public static final String ACCOUNT_NON_EXPIRED = "account_non_expired";

    public static final String ACCOUNT_NON_LOCKED = "account_non_locked";

    public static final String CREDENTIALS_NON_EXPIRED = "credentials_non_expired";

    public static final String ENABLED = "enabled";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
