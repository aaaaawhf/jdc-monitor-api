package com.g1335333249.jdc.monitor.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/31 15:02
 * @Description:
 * @Modified By:
 */
@Data
public class ServerJRes implements Serializable {

    /**
     * code : 0
     * message :
     * data : {"pushid":"11498739","readkey":"SCTIQrtV7uCOCPc","error":"SUCCESS","errno":0}
     */

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * pushid : 11498739
         * readkey : SCTIQrtV7uCOCPc
         * error : SUCCESS
         * errno : 0
         */

        @SerializedName("pushid")
        private String pushid;
        @SerializedName("readkey")
        private String readkey;
        @SerializedName("error")
        private String error;
        @SerializedName("errno")
        private int errno;
    }
}
