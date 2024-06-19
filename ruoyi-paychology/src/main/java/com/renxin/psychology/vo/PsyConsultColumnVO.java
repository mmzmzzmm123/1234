package com.renxin.psychology.vo;

import com.renxin.common.core.domain.BaseValObj;
import lombok.*;

import java.io.Serializable;

/**
 * 咨询专栏对象 psy_consult_column
 *
 * @author renxin
 * @date 2023-09-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultColumnVO extends BaseValObj implements Serializable
{
    private static final long serialVersionUID = 2030515186868682613L;

    /** 咨询师 */
    private Long consultId;
    private String consultName;

    /** 发布时间 */
    private String startTime; //开始时间
    private String endTime; //结束时间

    /** 标题 */
    private String title;

    /** 简介 */
    private String info;

    /** 0-文章 1-小组 */
    private String cat;

    /** 排序 */
    private Long sort;

    /** 封面 */
    private String img;

    /** 跳转url */
    private String url;

    /** 状态（0正常 1停用） */
    private String status;

}
