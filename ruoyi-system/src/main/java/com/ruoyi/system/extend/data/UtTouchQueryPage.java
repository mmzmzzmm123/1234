package com.ruoyi.system.extend.data;

import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
public class UtTouchQueryPage {
    /**
     * 当前页
     */
    private Integer pageIndex;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序方式（1升序、0降序）
     */
    private Integer sortOrder;
}
