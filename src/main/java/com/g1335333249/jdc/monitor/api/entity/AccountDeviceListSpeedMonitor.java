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
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account_device_list_speed_monitor")
public class AccountDeviceListSpeedMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备ID
     */
    @TableField("account_device_list_id")
    private Long accountDeviceListId;

    /**
     * 上传速度
     */
    @TableField("upload")
    private Long upload;

    /**
     * 下载速度
     */
    @TableField("download")
    private Long download;

    /**
     * CPU占用百分比
     */
    @TableField("cpu")
    private Double cpu;

    /**
     * 内存使用量
     */
    @TableField("mem")
    private Integer mem;

    /**
     * 发生时间
     */
    @TableField("event_time")
    private Date eventTime;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * WAN口IP
     */
    @TableField("wanip")
    private String wanip;


    public static final String ID = "id";

    public static final String ACCOUNT_DEVICE_LIST_ID = "account_device_list_id";

    public static final String UPLOAD = "upload";

    public static final String DOWNLOAD = "download";

    public static final String CPU = "cpu";

    public static final String MEM = "mem";

    public static final String EVENT_TIME = "event_time";

    public static final String CREATE_TIME = "create_time";

    public static final String WANIP = "wanip";

}
