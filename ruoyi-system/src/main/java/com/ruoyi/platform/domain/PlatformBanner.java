package com.ruoyi.platform.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台广告图对象 bus_platform_banner
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-平台广告图对象")
public class PlatformBanner extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 跳转链接 */
    @Excel(name = "跳转链接")
    @ApiModelProperty("跳转链接")
    private String toLink;

    /** 图片地址 */
    @Excel(name = "图片地址")
    @ApiModelProperty("图片地址")
    private String imgUrl;

    /** 主题色 */
    @Excel(name = "主题色")
    @ApiModelProperty("主题色")
    private String themeColor;

    /** 投放位置 */
    @Excel(name = "投放位置", dictType = "banner_position")
    @ApiModelProperty("投放位置")
    private String position;

    @Excel(name = "投放状态", dictType = "sys_show_hide")
    @ApiModelProperty("投放状态")
    private String state;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("开始时间")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
    private Date endTime;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;


}
