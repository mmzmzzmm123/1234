package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 咨询服务对象 psy_consult_serve
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_serve")
public class PsyConsultServe extends BasePlusEntity implements Serializable
{

    private static final long serialVersionUID = -6225882703437217633L;

    /** 咨询师 */
    @Excel(name = "咨询师")
    private Long consultId;

    /** 服务名称 */
    @Excel(name = "服务名称")
    private String name;

    /** 服务介绍 */
    @Excel(name = "服务介绍")
    private String info;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
    
}
