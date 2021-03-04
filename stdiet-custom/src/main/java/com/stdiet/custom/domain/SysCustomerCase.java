package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 客户案例管理对象 sys_customer_case
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
@Data
public class SysCustomerCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 案例名称 */
    @Excel(name = "案例名称")
    private String name;

    /** 案例关键词 */
    @Excel(name = "案例关键词")
    private String keyword;

    /** 案例所属客户ID */
    @Excel(name = "案例所属客户ID")
    private Long customerId;

    /** 删除标识 0未删除 1已删除，默认0 */
    private Long delFlag;
}