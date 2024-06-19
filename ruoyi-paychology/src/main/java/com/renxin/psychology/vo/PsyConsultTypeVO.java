package com.renxin.psychology.vo;

import com.renxin.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 咨询类型对象 psy_consult_type
 * 
 * @author renxin
 * @date 2023-07-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultTypeVO extends BaseValObj implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 父级ID */
    private Long pid;

    /** 路径 */
    private String path;

    /** 类型名称 */
    private String name;

    /** 类型，1 咨询类型 6 其他 */
    private String type;

    /** 排序 */
    private Long sort;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;
}