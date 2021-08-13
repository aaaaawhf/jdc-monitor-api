package com.g1335333249.jdc.monitor.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

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
@TableName("system_authority")
public class SystemAuthority implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "auth_id", type = IdType.AUTO)
    private Long authId;

    /**
     * 权限名称
     */
    @TableField("auth_name")
    private String authName;

    /**
     * 上级权限ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 权限代码
     */
    @TableField("auth_code")
    private String authCode;

    /**
     * 权限路径
     */
    @TableField("auth_uri")
    private String authUri;

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

    /**
     * 是否选中
     */
    @TableField(value = "checked", exist = false)
    private Boolean checked;

    /**
     * 子权限
     */
    @TableField(value = "sub_system_authority", exist = false)
    private List<SystemAuthority> subSystemAuthority;

    public static final String AUTH_ID = "auth_id";

    public static final String PARENT_ID = "parent_id";

    public static final String AUTH_NAME = "auth_name";

    public static final String AUTH_CODE = "auth_code";

    public static final String AUTH_URI = "auth_uri";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

    @Override
    public String getAuthority() {
        return authCode;
    }
}
