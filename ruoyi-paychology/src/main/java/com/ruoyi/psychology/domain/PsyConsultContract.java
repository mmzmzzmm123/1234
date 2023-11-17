package com.ruoyi.psychology.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 咨询师合同协议对象 psy_consult_contract
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_contract")
public class PsyConsultContract extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = -6584654446216550752L;

    /** 合同编号 */
    private Long id;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String name;

    /** 咨询师 */
    @Excel(name = "咨询师")
    private Long consultId;

    /** 签约人 */
    @Excel(name = "签约人")
    private String consultName;

    /** 入驻类型 */
    @Excel(name = "入驻类型")
    private Integer type;

    /** 入驻费用 */
    @Excel(name = "入驻费用")
    private BigDecimal money;

    /** 结算比例 */
    @Excel(name = "结算比例")
    private BigDecimal ratio;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 终止原因 */
    @Excel(name = "终止原因")
    private String memo1;

    /** 撤销原因 */
    @Excel(name = "撤销原因")
    private String memo2;

    /** 1-待签署,2-已撤销,3-逾期未签署,4-待生效,5-生效中,6-已失效,7-终止 */
    @Excel(name = "1-待签署,2-已撤销,3-逾期未签署,4-待生效,5-生效中,6-已失效,7-终止")
    private String status;

}
