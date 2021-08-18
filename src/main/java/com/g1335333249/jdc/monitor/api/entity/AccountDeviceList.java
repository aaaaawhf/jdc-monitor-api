package com.g1335333249.jdc.monitor.api.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@TableName("account_device_list")
public class AccountDeviceList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户账户ID
     */
    @TableField("user_account_id")
    private Long userAccountId;

    /**
     * 产品ID
     */
    @TableField("product_id")
    private String productId;

    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 今日积分收入
     */
    @TableField("today_point_income")
    private Integer todayPointIncome;

    /**
     * 总积分收入
     */
    @TableField("all_point_income")
    private Integer allPointIncome;

    /**
     * 成本
     */
    @TableField("cost")
    private Double cost;

    @TableField("status")
    private String status;

    @TableField("config_type")
    private Integer configType;

    @TableField("device_type")
    private Integer deviceType;

    @TableField("cid")
    private Integer cid;

    @TableField("p_img_url")
    private String pImgUrl;

    @TableField("device_name")
    private String deviceName;

    @TableField("feed_id")
    private Long feedId;

    @TableField("version")
    private String version;

    @TableField("own_flag")
    private Integer ownFlag;

    @TableField("device_add_time")
    private Date deviceAddTime;

    @TableField("access_key")
    private String accessKey;

    @TableField("device_page_type")
    private String devicePageType;

    @TableField("cname")
    private String cname;

    @TableField("rom")
    private String rom;

    @TableField("sn")
    private String sn;

    @TableField("model")
    private String model;

    @TableField("model_name")
    private String modelName;

    @TableField("ap_mode")
    private String apMode;

    @TableField("online_time")
    private Long onlineTime;

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

    @TableField(value = "now_upload", exist = false)
    private Long nowUpload;

    @TableField(value = "now_download", exist = false)
    private Long nowDownload;

    @TableField(value = "total_upload", exist = false)
    private Long totalUpload;

    @TableField(value = "total_download", exist = false)
    private Long totalDownload;

    @TableField(exist = false)
    private String onlineTimeStr;

    public String getOnlineTimeStr() {
        return DateUtil.formatBetween(onlineTime * 1000);
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String USER_ACCOUNT_ID = "user_account_id";

    public static final String PRODUCT_ID = "product_id";

    public static final String DEVICE_ID = "device_id";

    public static final String TODAY_POINT_INCOME = "today_point_income";

    public static final String ALL_POINT_INCOME = "all_point_income";

    public static final String COST = "cost";

    public static final String STATUS = "status";

    public static final String CONFIG_TYPE = "config_type";

    public static final String DEVICE_TYPE = "device_type";

    public static final String CID = "cid";

    public static final String P_IMG_URL = "p_img_url";

    public static final String DEVICE_NAME = "device_name";

    public static final String FEED_ID = "feed_id";

    public static final String VERSION = "version";

    public static final String OWN_FLAG = "own_flag";

    public static final String DEVICE_ADD_TIME = "device_add_time";

    public static final String ACCESS_KEY = "access_key";

    public static final String DEVICE_PAGE_TYPE = "device_page_type";

    public static final String CNAME = "cname";

    public static final String ROM = "rom";

    public static final String SN = "sn";

    public static final String MODEL = "model";

    public static final String MODEL_NAME = "model_name";

    public static final String AP_MODE = "ap_mode";

    public static final String ONLINE_TIME = "online_time";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

}
