package com.g1335333249.jdc.monitor.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user_authority")
public class SystemUserAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户-权限关联ID
     */
    @TableId(value = "user_auth_id", type = IdType.AUTO)
    private Long userAuthId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 权限ID
     */
    @TableField("auth_id")
    private Long authId;

    /**
     * 是否有效
     */
    @TableField("is_valid")
    private Boolean isValid;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @TableField("create_id")
    private Long createId;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改人ID
     */
    @TableField("update_id")
    private Long updateId;


    public static final String USER_AUTH_ID = "user_auth_id";

    public static final String USER_ID = "user_id";

    public static final String AUTH_ID = "auth_id";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

}
