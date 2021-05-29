package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 业务提成比例对象 sys_commision
 *
 * @author wonder
 * @date 2020-09-24
 */
@Data
public class SysCommision extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long ruleId;

    /**
     * 业务员
     */
    private Long userId;

    private List<Long> userIds;

    /**
     * 岗位id
     */
    private Long postId;

    /**
     * 业务员
     */
    @Excel(name = "业务员")
    private String userName;

    @Excel(name = "岗位")
    private String postName;

    /**
     * 金额
     */
    @Excel(name = "金额", scale = 2)
    private BigDecimal amount;

    /**
     * 分成比例
     */
    @Excel(name = "比例", suffix = "%")
    private Float rate;

    /** 比例开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 提成
     */
    @Excel(name = "提成", scale = 2)
    private BigDecimal commision;

    //订单审核状态
    private String reviewStatus;

    //服务开始时间，用于计算该时间段的提成
    private String serverScopeStartTime;

    //服务结束时间，用于计算该时间段的提成
    private String serverScopeEndTime;

    //是否查询替换订单
    private Boolean replaceOrderFlag;
}
