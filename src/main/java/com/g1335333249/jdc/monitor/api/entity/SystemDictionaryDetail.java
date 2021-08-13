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
@TableName("system_dictionary_detail")
public class SystemDictionaryDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dictionary_detail_id", type = IdType.AUTO)
    private Long dictionaryDetailId;

    /**
     * 字典ID
     */
    @TableField("dictionary_id")
    private Long dictionaryId;

    /**
     * 字典详情标签
     */
    @TableField("dictionary_detail_label")
    private String dictionaryDetailLabel;

    /**
     * 字典详情键值
     */
    @TableField("dictionary_detail_value")
    private String dictionaryDetailValue;

    /**
     * 字典详情状态
     */
    @TableField("dictionary_detail_status")
    private Boolean dictionaryDetailStatus;

    /**
     * 字典详情排序
     */
    @TableField("dictionary_detail_sort")
    private Integer dictionaryDetailSort;

    /**
     * 是否默认（每个类型只能有一个默认）
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 显示样式
     */
    @TableField("show_class")
    private String showClass;

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


    public static final String DICTIONARY_DETAIL_ID = "dictionary_detail_id";

    public static final String DICTIONARY_ID = "dictionary_id";

    public static final String DICTIONARY_DETAIL_LABEL = "dictionary_detail_label";

    public static final String DICTIONARY_DETAIL_VALUE = "dictionary_detail_value";

    public static final String DICTIONARY_DETAIL_STATUS = "dictionary_detail_status";

    public static final String DICTIONARY_DETAIL_SORT = "dictionary_detail_sort";

    public static final String IS_DEFAULT = "is_default";

    public static final String SHOW_CLASS = "show_class";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

}
