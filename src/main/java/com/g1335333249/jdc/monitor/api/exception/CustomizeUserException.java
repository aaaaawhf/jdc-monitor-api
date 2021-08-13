package com.g1335333249.jdc.monitor.api.exception;

/**
 * @author guanpeng
 * @description 自定义用户异常
 * @date 2020/4/26 11:13 上午
 * @since
 */
public class CustomizeUserException extends CustomizeException {
    public CustomizeUserException(String message) {
        super(message);
    }

    public CustomizeUserException(String message, Throwable cause) {
        super(message, cause);
    }

}
