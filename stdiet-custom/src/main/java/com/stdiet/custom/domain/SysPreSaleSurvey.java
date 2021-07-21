package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 售前调查，销售给客户发送简易信息调查链接对象 sys_pre_sale_survey
 *
 * @author xzj
 * @date 2021-07-05
 */
@Data
public class SysPreSaleSurvey extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户唯一标识 */
    @Excel(name = "客户唯一标识")
    private String customerKey;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 身高，厘米 */
    @Excel(name = "身高，厘米")
    private Integer tall;

    /** 体重 */
    @Excel(name = "体重")
    private BigDecimal weight;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 0男 1女 */
    @Excel(name = "0男 1女")
    private Integer sex;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 特殊体征ID */
    @Excel(name = "特殊体征ID")
    private String physicalSignsId;

    /** 其他体征 */
    @Excel(name = "其他体征")
    private String otherPhysicalSigns;

    /** 作息时间 */
    @Excel(name = "作息时间")
    private String timeTable;

    /** 减脂或慢病调理经历 */
    @Excel(name = "减脂或慢病调理经历")
    private String experience;

    /** 职业 */
    @Excel(name = "职业")
    private String occupation;

    /** 气血数据 */
    @Excel(name = "气血数据")
    private String bloodData;

    /** 湿气数据 */
    @Excel(name = "湿气数据")
    private String moistureData;

    /** 售前ID */
    @Excel(name = "售前ID")
    private Long preSaleId;

    /** 问卷类型 0售前简易问卷  1简易评估问卷 */
    @Excel(name = "问卷类型 0售前简易问卷  1简易评估问卷")
    private Integer questionType;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;

    /**
     * 标准体重
     */
    private Double standardWeight;
}
