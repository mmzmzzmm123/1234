package com.ruoyi.staff.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工动态对象 bus_staff_trends
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工动态对象")
public class StaffTrends extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识/店员标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    @ApiModelProperty("店员信息")
    private StaffInfo staffInfo;

    /** 文本 */
    @Excel(name = "文本")
    @ApiModelProperty("文本")
    private String content;

    /** 图片集合 */
    @Excel(name = "图片集合")
    @ApiModelProperty("图片集合")
    private String imgList;

    /** 视频地址 */
    @Excel(name = "视频地址")
    @ApiModelProperty("视频地址")
    private String videoUrl;

    /** 语音地址 */
    @Excel(name = "语音地址")
    @ApiModelProperty("语音地址")
    private String voiceUrl;

    /** 语言时长 */
    @Excel(name = "语言时长")
    @ApiModelProperty("语言时长")
    private Long voiceTime;

    /** 点赞数 */
    @Excel(name = "点赞数")
    @ApiModelProperty("点赞数")
    private Long likeNum;


}
