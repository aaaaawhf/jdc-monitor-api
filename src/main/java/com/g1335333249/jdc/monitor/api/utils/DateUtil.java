package com.g1335333249.jdc.monitor.api.utils;

import java.text.SimpleDateFormat;

/**
 * @author guanpeng
 * @description TODO
 * @date 2020/11/16 10:21 上午
 * @since
 */
public class DateUtil {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_HM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_MD_CHINESE = new SimpleDateFormat("MM月dd日");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_DATE_CHINESE = new SimpleDateFormat("yyyy年MM月dd日");

}
