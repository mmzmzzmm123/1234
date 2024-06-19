package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 咨询类型对象 psy_consult_type
 * 
 * @author renxin
 * @date 2023-07-14
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_type")
public class PsyConsultType extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 类型ID */
    private Long id;

    /** 父级ID */
    @Excel(name = "父级ID")
    private Long pid;

    /** 路径 */
    @Excel(name = "路径")
    private String path;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String name;

    /** 类型，1 咨询类型 6 其他 */
    @Excel(name = "类型，1 咨询类型 6 其他")
    private String type;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 子类型 */
    @TableField(exist = false)
    private List<PsyConsultType> children = new ArrayList<>();

}
