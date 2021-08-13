package com.g1335333249.jdc.monitor.api.utils;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author guanpeng
 * @description TODO
 * @date 2020/4/24 4:51 下午
 * @since
 */
public class ResponseUtil {
    public static void successToJson(HttpServletResponse response, Object res) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Result.success(res).toJson());
    }

    public static void successToJson(HttpServletResponse response, int code, Object res) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Result.success(code, res).toJson());
    }

    public static void failToJson(HttpServletResponse response, Object res) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Result.fail(res).toJson());
    }

    public static void failToJson(HttpServletResponse response, int code, Object res) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Result.fail(code, res).toJson());
    }

    public static void failToJson(HttpServletResponse response, int code, String res) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Result.fail(code, res).toJson());
    }


}
