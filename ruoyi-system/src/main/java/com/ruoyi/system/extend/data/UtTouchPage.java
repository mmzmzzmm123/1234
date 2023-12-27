package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class UtTouchPage<T> {

    /**
     * 记录总数
     */
    private Integer recordCount;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 数据字段
     */
    private List<T> dataList;

    /**
     * 附加字段
     */
    private String additionalVal;


}
