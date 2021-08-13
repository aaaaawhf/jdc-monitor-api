package com.g1335333249.jdc.monitor.api.exception;

import java.text.MessageFormat;

/**
 * @author guanpeng
 * @description 角色已存在异常
 * @date 2020/4/26 11:13 上午
 * @since
 */
public class CustomizeAuthExistsException extends CustomizeException {
    private static String messageTemplate = "角色[{0}]已存在";

    public CustomizeAuthExistsException(String message) {
        super(MessageFormat.format(messageTemplate, message));
    }

    public CustomizeAuthExistsException(String message, Throwable cause) {
        super(MessageFormat.format(messageTemplate, message), cause);
    }

}
