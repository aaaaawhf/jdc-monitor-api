package com.g1335333249.jdc.monitor.api.model.jdc.router;

import lombok.Data;

@Data
public class AppRouterPointRecord {
    /**
     * recordType : 1
     * exchangeType : 1
     * pointAmount : 10
     * beanAmount : 0
     * createTime : 1625264979000
     */

    private int recordType;
    private int exchangeType;
    private int pointAmount;
    private int beanAmount;
    private long createTime;
}
