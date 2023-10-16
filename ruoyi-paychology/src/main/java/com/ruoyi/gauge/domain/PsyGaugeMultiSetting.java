package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 心理测评多维设置对象 psy_gauge_multi_setting
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Data
public class PsyGaugeMultiSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 测评表单ID */
    @Excel(name = "测评表单ID")
    private Integer gaugeId;

    /** 纬度名 */
    @Excel(name = "纬度名")
    private String name;

    /** x */
    private Integer x;
    /** y */
    private Integer y;
    /** 描述 */
    private String info;
    /** 背景色 */
    private String line;
    /** 颜色 */
    private String prd;

    /** 绑定问题ID */
    @Excel(name = "绑定问题ID")
    private String questionIds;

    @Excel(name = "得分设置ID")
    private String scoreSettingIds;

}
