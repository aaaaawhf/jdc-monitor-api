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
@TableName("visit_log")
public class VisitLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访问URI
     */
    @TableField("visit_uri")
    private String visitUri;

    /**
     * 访问时间
     */
    @TableField("visit_date")
    private Date visitDate;

    /**
     * 访问用户名
     */
    @TableField("visit_username")
    private String visitUsername;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 访问IP
     */
    @TableField("visit_ip")
    private String visitIp;

    /**
     * 访问地址
     */
    @TableField("visit_address")
    private String visitAddress;

    /**
     * 访问浏览器UA
     */
    @TableField("visit_browser")
    private String visitBrowser;


    public static final String ID = "id";

    public static final String VISIT_URI = "visit_uri";

    public static final String VISIT_DATE = "visit_date";

    public static final String VISIT_USERNAME = "visit_username";

    public static final String REMARK = "remark";

    public static final String VISIT_IP = "visit_ip";

    public static final String VISIT_ADDRESS = "visit_address";

    public static final String VISIT_BROWSER = "visit_browser";

}
