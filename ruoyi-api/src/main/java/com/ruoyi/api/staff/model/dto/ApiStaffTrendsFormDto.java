package com.ruoyi.api.staff.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/3 23:50
 */
@ApiModel("api-店员动态表单")
@Data
@Accessors(chain = true)
public class ApiStaffTrendsFormDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文本")
    private String content;

    @ApiModelProperty("图片集合")
    private String imgList;

    @ApiModelProperty("视频地址")
    private String videoUrl;

    @ApiModelProperty("语音地址")
    private String voiceUrl;

    @ApiModelProperty("语音文件")
    private MultipartFile voiceFile;

    @ApiModelProperty("ossKey")
    private String ossKey;

    @ApiModelProperty("语言时长")
    private Long voiceTime;
}
