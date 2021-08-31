package com.g1335333249.jdc.monitor.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/31 14:28
 * @Description:
 * @Modified By:
 */
@Data
public class BarkRes implements Serializable {

    /**
     * code : 200
     * message : success
     * timestamp : 1630390498
     */

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("timestamp")
    private int timestamp;
}
