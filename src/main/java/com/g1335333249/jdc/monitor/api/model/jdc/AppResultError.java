package com.g1335333249.jdc.monitor.api.model.jdc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AppResultError {
    /**
     * errorInfo : token invalid
     * errorCode : 401
     */

    @SerializedName("errorInfo")
    private String errorInfo;
    @SerializedName("errorCode")
    private String errorCode;
}
