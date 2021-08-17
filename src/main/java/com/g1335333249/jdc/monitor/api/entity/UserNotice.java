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
 * @since 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_notice")
public class UserNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 通知类型：1-server酱
     */
    @TableField("notice_type")
    private Integer noticeType;

    /**
     * 发送通知的key
     */
    @TableField("send_key")
    private String sendKey;

    @TableField("is_enabled")
    private Boolean isEnabled;

    @TableField("is_valid")
    private Boolean isValid;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user_id")
    private Long createUserId;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user_id")
    private Long updateUserId;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NOTICE_TYPE = "notice_type";

    public static final String SEND_KEY = "send_key";

    public static final String IS_ENABLED = "is_enabled";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

}
