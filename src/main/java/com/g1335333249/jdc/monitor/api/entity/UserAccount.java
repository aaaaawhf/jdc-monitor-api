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
 * @since 2021-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 京东账号pin
     */
    @TableField("pin")
    private String pin;

    /**
     * 京东账号tgt
     */
    @TableField("tgt")
    private String tgt;

    /**
     * 设备数量
     */
    @TableField("device_count")
    private Integer deviceCount;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否有效
     */
    @TableField("is_valid")
    private Boolean isValid;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 添加用户ID
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新用户ID
     */
    @TableField("update_user_id")
    private Long updateUserId;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String PIN = "pin";

    public static final String TGT = "tgt";

    public static final String DEVICE_COUNT = "device_count";

    public static final String IS_VALID = "is_valid";

    public static final String REMARK = "remark";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

}
