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
 * 咨询服务配置对象 psy_consult_serve_config
 * 
 * @author ruoyi
 * @date 2023-07-14
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_serve_config")
public class PsyConsultServeConfig extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 咨询形式 语音咨询、视频咨询、面对面咨询 */
    @Excel(name = "咨询形式 语音咨询、视频咨询、面对面咨询")
    private Integer mode;

    /** 服务类型 单次咨询 套餐咨询 */
    @Excel(name = "服务类型 单次咨询 套餐咨询")
    private Integer type;

    /** 服务名称 */
    @Excel(name = "服务名称")
    private String name;

    /** 服务介绍 */
    @Excel(name = "服务介绍")
    private String info;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 时长 */
    @Excel(name = "时长")
    private Integer time;

    /** 数量 */
    @Excel(name = "数量")
    private Integer num;

    /** 排序 倒叙 */
    @Excel(name = "排序 倒叙")
    private Integer zIndex;

    /** 限购 0-不限制 1-限制 */
    @Excel(name = "限购 0-不限制 1-限制")
    private Integer bound;

    /** 销量 */
    @Excel(name = "销量")
    private Integer sales;

    /** 咨询师数量 */
    @Excel(name = "咨询师数量")
    private Integer ref;

    /** 有效期 */
    @Excel(name = "有效期")
    private Integer end;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
