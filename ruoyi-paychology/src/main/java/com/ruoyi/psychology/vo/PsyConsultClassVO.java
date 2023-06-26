package com.ruoyi.psychology.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import com.ruoyi.common.core.domain.BaseValObj;
import lombok.*;

import java.io.Serializable;

/**
 * 咨询类型对象 psy_consult_class
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultClassVO extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = 2656547953531703124L;
    /** 名称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 图标路径 */
    private String url;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;

}
