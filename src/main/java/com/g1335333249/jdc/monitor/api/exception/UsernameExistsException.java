package com.g1335333249.jdc.monitor.api.exception;

import java.text.MessageFormat;

/**
 * @author guanpeng
 * @description 用户已存在异常
 * @date 2020/4/26 11:13 上午
 * @since
 */
public class UsernameExistsException extends CustomizeUserException {
    private static String messageTemplate = "用户名[{0}]已存在";

    public UsernameExistsException(String message) {
        super(MessageFormat.format(messageTemplate, message));
    }

    public UsernameExistsException(String message, Throwable cause) {
        super(MessageFormat.format(messageTemplate, message), cause);
    }

}
