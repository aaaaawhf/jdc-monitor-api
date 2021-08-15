package com.g1335333249.jdc.monitor.api.model.request;

import lombok.Data;

/**
 * @author guanpeng
 */
@Data
public class UserAccountRequestParam {
    private String pin;
    private String tgt;
    private int page;
    private int size;
    private Long id;
    private String remark;
}
