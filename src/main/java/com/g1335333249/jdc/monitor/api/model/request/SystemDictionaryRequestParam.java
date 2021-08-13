package com.g1335333249.jdc.monitor.api.model.request;

import lombok.Data;

/**
 * @author guanpeng
 * @description 系统字典请求参数
 * @date 2021/1/18 5:31 下午
 * @since
 */
@Data
public class SystemDictionaryRequestParam {
    private String searchWord;
    private Long dictionaryId;
    private Integer page;
    private Integer size;
    private String orderColumn;
    private Boolean isAsc;
}
