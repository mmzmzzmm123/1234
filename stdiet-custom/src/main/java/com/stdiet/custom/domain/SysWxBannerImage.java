package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 小程序banner图对象 sys_wx_banner_image
 *
 * @author xzj
 * @date 2021-07-13
 */
@Data
public class SysWxBannerImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** banner图标题 */
    @Excel(name = "banner图标题")
    private String title;

    /** banner图内容 */
    @Excel(name = "banner图内容")
    private String content;

    /** banner图片地址 */
    @Excel(name = "banner图片地址")
    private String bannerUrl;

    /** 点击跳转地址 */
    @Excel(name = "点击跳转地址")
    private String jumpUrl;

    /** 展示标识 0不展示 1展示 */
    @Excel(name = "展示标识 0不展示 1展示")
    private Long showFlag;

    /** 顺序优先级，0-100 */
    @Excel(name = "顺序优先级，0-100")
    private Long orderNum;

    /** 删除标识 0未删除 1已删除 */
    private Long delFlag;
}