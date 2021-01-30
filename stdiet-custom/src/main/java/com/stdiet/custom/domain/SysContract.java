package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 合同对象 sys_contract
 *
 * @author wonder
 * @date 2020-10-23
 */
@Data
public class SysContract extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private Long id;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 调理项目ID
     */
    private Long projectId;

    /**
     * 调理项目名称
     */
    private String projectName;

    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String name;

    /**
     * 客户签名
     */
    private String signName;

    /**
     * 客户身份证
     */
    private String cusId;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;

    /**
     * 服务时间
     */
    @Excel(name = "服务时间")
    private int serveTime;

    private String serveTimeStr;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amount;

    private String amountUpper;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String path;

    /**
     * 签约日期
     */
    private String signDate;

    /**
     * 营养师
     */
    private String tutor;

    /**
     * 合同状态
     * 0-未签订；1-已签订
     */
    private String Status;

    private String servePromise;

    /**
     * 删除标识
     */
    private Integer delFlag;

}
