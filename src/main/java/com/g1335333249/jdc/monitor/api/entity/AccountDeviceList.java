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

    /**
     * 公网IP
     */
    @TableField("internet_ip")
    private String internetIp;

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

    /**
     * 插件1名称
     */
    @TableField("plugin_one_nickname")
    private String pluginOneNickname;

    /**
     * 插件1名称
     */
    @TableField("plugin_one_name")
    private String pluginOneName;

    /**
     * 插件1缓存大小
     */
    @TableField("plugin_one_cache_size")
    private Long pluginOneCacheSize;

    /**
     * 插件1运行状态
     */
    @TableField("plugin_one_status")
    private String pluginOneStatus;

    /**
     * 插件1是否外置
     */
    @TableField("plugin_one_is_ext")
    private Boolean pluginOneIsExt;

    /**
     * 插件1运行位置
     */
    @TableField("plugin_one_run_pos")
    private String pluginOneRunPos;

    /**
     * 插件2名称
     */
    @TableField("plugin_two_nickname")
    private String pluginTwoNickname;

    /**
     * 插件2名称
     */
    @TableField("plugin_two_name")
    private String pluginTwoName;

    /**
     * 插件2缓存大小
     */
    @TableField("plugin_two_cache_size")
    private Long pluginTwoCacheSize;

    /**
     * 插件2运行状态
     */
    @TableField("plugin_two_status")
    private String pluginTwoStatus;

    /**
     * 插件2是否外置
     */
    @TableField("plugin_two_is_ext")
    private Boolean pluginTwoIsExt;

    /**
     * 插件2运行位置
     */
    @TableField("plugin_two_run_pos")
    private String pluginTwoRunPos;

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
        return onlineTime == null ? "设备不在线" : DateUtil.formatBetween(onlineTime * 1000);
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

    public static final String INTERNET_IP = "internet_ip";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String PLUGIN_ONE_NICKNAME = "plugin_one_nickname";

    public static final String PLUGIN_ONE_NAME = "plugin_one_name";

    public static final String PLUGIN_ONE_CACHE_SIZE = "plugin_one_cache_size";

    public static final String PLUGIN_ONE_STATUS = "plugin_one_status";

    public static final String PLUGIN_ONE_IS_EXT = "plugin_one_is_ext";

    public static final String PLUGIN_ONE_RUN_POS = "plugin_one_run_pos";

    public static final String PLUGIN_TWO_NICKNAME = "plugin_two_nickname";

    public static final String PLUGIN_TWO_NAME = "plugin_two_name";

    public static final String PLUGIN_TWO_CACHE_SIZE = "plugin_two_cache_size";

    public static final String PLUGIN_TWO_STATUS = "plugin_two_status";

    public static final String PLUGIN_TWO_IS_EXT = "plugin_two_is_ext";

    public static final String PLUGIN_TWO_RUN_POS = "plugin_two_run_pos";

}
