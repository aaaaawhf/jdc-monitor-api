package com.g1335333249.jdc.monitor.api.model.jdc;

import lombok.Data;

import java.util.List;

@Data
public class AppDeviceResult {
    private Integer count;
    private List<AppDeviceInfo> list;
}
