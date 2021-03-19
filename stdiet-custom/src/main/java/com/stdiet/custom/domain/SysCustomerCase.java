package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

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

    private Long[] ids;

    /** 案例名称 */
    @Excel(name = "案例名称")
    private String name;

    /** 案例关键词 */
    @Excel(name = "关键词")
    private String keyword;

    private String[] keywordArray;

    /** 案例所属客户ID */
    private Long customerId;

    @Excel(name = "所属客户")
    private String customerName;

    //是否显示到微信小程序
    private Boolean wxShow;

    /** 删除标识 0未删除 1已删除，默认0 */
    private Long delFlag;

    /**
     * 文件列表
     */
    private List<SysCustomerCaseFile> caseFileList;

    //案例文件名称数组
    private String[] caseFileName;

    //案例文件URL数组
    private String[] caseFileUrl;
}