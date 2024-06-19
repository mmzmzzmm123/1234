package com.renxin.psychology.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 咨询配置对象
 * 
 * @author renxin
 * @date 2023-06-16
 */
@Data
public class PsyConsultConfigVO implements Serializable
{
    /** banner图片名称 */
    private String name;

    /** 跳转url */
    private String value;

    /** 标题 */
    private String title;

    /** code */
    private String code;

    private Boolean flag = false;

}
