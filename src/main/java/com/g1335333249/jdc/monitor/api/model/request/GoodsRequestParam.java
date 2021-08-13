package com.g1335333249.jdc.monitor.api.model.request;

import lombok.Data;

import java.util.List;

/**
 * @author guanpeng
 * @description 客户列表请求参数
 * @date 2021/1/18 5:31 下午
 * @since
 */
@Data
public class GoodsRequestParam {
    private String searchWord;
    private Long userId;
    private Long goodsId;
    private List<Long> goodsIds;
    private Integer page;
    private Integer size;
    private String orderColumn;
    private Boolean isAsc;
}
