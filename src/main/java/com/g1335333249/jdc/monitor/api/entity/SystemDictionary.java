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
@TableName("system_dictionary")
public class SystemDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dictionary_id", type = IdType.AUTO)
    private Long dictionaryId;

    /**
     * 字典名称
     */
    @TableField("dictionary_name")
    private String dictionaryName;

    /**
     * 字典代码
     */
    @TableField("dictionary_code")
    private String dictionaryCode;

    /**
     * 是否系统默认（不许删除）
     */
    @TableField("system_default")
    private Boolean systemDefault;

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
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
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


    public static final String DICTIONARY_ID = "dictionary_id";

    public static final String DICTIONARY_NAME = "dictionary_name";

    public static final String DICTIONARY_CODE = "dictionary_code";

    public static final String SYSTEM_DEFAULT = "system_default";

    public static final String REMARK = "remark";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

}
