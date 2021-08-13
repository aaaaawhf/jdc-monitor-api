package com.g1335333249.jdc.monitor.api.exception;

/**
 * @author guanpeng
 * @description 自定义异常
 * @date 2020/4/26 11:08 上午
 * @since
 */
public class CustomizeException extends RuntimeException {

    public CustomizeException(String message) {
        super(message);
    }

    public CustomizeException(String message, Throwable cause) {
        super(message, cause);
    }

}
