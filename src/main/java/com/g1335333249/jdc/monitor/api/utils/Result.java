package com.g1335333249.jdc.monitor.api.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guanpeng
 * @date 2019-09-17 09:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "通用返回数据", description = "通用返回数据")
public class Result<T> {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @ApiModelProperty(value = "Http状态码")
    private Integer code;
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "状态")
    private String statue;
    @ApiModelProperty(value = "时间戳")
    private long timestamp;
    @ApiModelProperty(value = "消息体")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private T data;


    public static <T> Result<T> success() {
        return Result.<T>builder().code(200).success(true).statue(SUCCESS)
                .timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Result<T> fail() {
        return Result.<T>builder().code(500).success(false).statue(FAIL)
                .timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder().code(200).success(true).statue(SUCCESS)
                .timestamp(System.currentTimeMillis()).data(data).build();
    }

    public static <T> Result<T> success(int code, T data) {
        return Result.<T>builder().code(code).success(true).statue(SUCCESS)
                .timestamp(System.currentTimeMillis()).data(data).build();
    }

    public static <T> Result<T> fail(String message) {
        return Result.<T>builder().code(500).success(false).statue(FAIL)
                .timestamp(System.currentTimeMillis()).message(message).build();
    }

    public static <T> Result<T> fail(T data) {
        return Result.<T>builder().code(500).success(false).statue(FAIL)
                .timestamp(System.currentTimeMillis()).data(data).build();
    }

    public static <T> Result<T> fail(int code, T data) {
        return Result.<T>builder().code(code).success(false).statue(FAIL)
                .timestamp(System.currentTimeMillis()).data(data).build();
    }

    public static <T> Result<T> fail(int code, String message) {
        return Result.<T>builder().code(code).success(false).statue(FAIL)
                .timestamp(System.currentTimeMillis()).message(message).build();
    }

    public String toJson() {
        return JsonUtil.GSON.toJson(this);
    }
}
