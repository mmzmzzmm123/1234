package com.ruoyi.api.staff.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/3 16:44
 */
@ApiModel("api-店员动态")
@Data
@Accessors(chain = true)
public class ApiStaffTrendsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("文本")
    private String content;

    @ApiModelProperty("图片集合")
    private String imgList;

    @ApiModelProperty("视频地址")
    private String videoUrl;

    @ApiModelProperty("语音地址")
    private String voiceUrl;

    @ApiModelProperty("语音播放状态（-1 暂停 0播放）")
    private int audioState = -1;

    @ApiModelProperty("语言时长")
    private Long voiceTime;

    @ApiModelProperty("点赞数")
    private Long likeNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("店员信息")
    private ApiStaffInfoVo staffInfoVo;
}
