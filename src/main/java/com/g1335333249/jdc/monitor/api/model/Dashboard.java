package com.g1335333249.jdc.monitor.api.model;

import lombok.Data;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/19 12:06
 * @Description:
 * @Modified By:
 */
@Data
public class Dashboard {
    private Integer totalDevices;
    private Integer onlineDevices;
    private Long totalUpload;
    private Long totalDownload;
    private Long totalLastUpload;
    private Long totalLastDownload;
    private Long todayPointIncome;
    private Long allPointIncome;
}
