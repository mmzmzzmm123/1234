package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询专栏对象 psy_consult_column
 *
 * @author renxin
 * @date 2023-09-04
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_column")
public class PsyConsultColumn extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = -3434865787322294564L;

    /** 咨询师 */
    @Excel(name = "咨询师")
    private Long consultId;
    private String consultName;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String info;

    /** 0-文章 1-小组 */
    @Excel(name = "0-文章 1-小组")
    private String cat;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 封面 */
    @Excel(name = "封面")
    private String img;

    /** 跳转url */
    @Excel(name = "跳转url")
    private String url;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

}
