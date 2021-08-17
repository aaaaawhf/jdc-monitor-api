package com.g1335333249.jdc.monitor.api.model.request;

import lombok.Data;

/**
 * @author guanpeng
 */
@Data
public class AccountDeviceListRequestParam {
    private Long id;
    private Double cost;
    private int page;
    private int size;
}
