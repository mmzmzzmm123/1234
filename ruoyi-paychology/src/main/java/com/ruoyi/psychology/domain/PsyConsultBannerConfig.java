package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询banner配置对象 psy_consult_banner_config
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_banner_config")
public class PsyConsultBannerConfig extends BasePlusEntity implements Serializable
{

    private static final long serialVersionUID = -7152259025751459836L;

    /** banner图片地址 */
    @Excel(name = "图片名称")
    private String name;

    /** banner图片地址 */
    @Excel(name = "banner图片地址")
    private String url;

    /** 跳转url */
    @Excel(name = "跳转url")
    private String linkUrl;

    /** banner分类(0-首页一级banner页) */
    @Excel(name = "banner分类(0-首页一级banner页)")
    private Integer type;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

}