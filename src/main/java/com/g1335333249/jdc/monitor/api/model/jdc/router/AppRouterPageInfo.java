package com.g1335333249.jdc.monitor.api.model.jdc.router;

import lombok.Data;

@Data
public class AppRouterPageInfo {
    /**
     * currentPage : 1
     * pageSize : 15
     * totalRecord : 2
     * totalPage : 1
     */

    private int currentPage;
    private int pageSize;
    private int totalRecord;
    private int totalPage;
}
