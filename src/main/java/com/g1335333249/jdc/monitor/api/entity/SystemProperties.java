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
@TableName("system_properties")
public class SystemProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("property_key")
    private String propertyKey;

    @TableField("property_value")
    private String propertyValue;

    @TableField("is_valid")
    private Integer isValid;

    @TableField("insert_date")
    private Date insertDate;


    public static final String ID = "id";

    public static final String PROPERTY_KEY = "property_key";

    public static final String PROPERTY_VALUE = "property_value";

    public static final String IS_VALID = "is_valid";

    public static final String INSERT_DATE = "insert_date";

}
