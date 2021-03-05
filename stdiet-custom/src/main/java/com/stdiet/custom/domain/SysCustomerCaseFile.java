package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 客户案例对应文件管理对象 sys_customer_case_file
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
@Data
public class SysCustomerCaseFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 案例文件 */
    @Excel(name = "案例文件")
    private Long caseId;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String fileUrl;

    /** 文件名字 */
    @Excel(name = "文件名字")
    private String fileName;

    /** 删除标识 0未删除 1已删除 */
    private Long delFlag;
}