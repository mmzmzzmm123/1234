package com.ruoyi.psychology.vo;

import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 咨询banner配置对象 psy_consulting_banner_config
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultingBannerConfigVO extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = 4435332839250909273L;

    /** banner图片名称 */
    private String name;

    /** banner图片地址 */
    private String url;

    /** 跳转url */
    private String linkUrl;

    /** banner分类(0-首页一级banner页) */
    private Integer type;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;

}
