package com.ruoyi.api.platform.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2022/4/14 15:14
 */
@ApiModel("api-应用广告图")
@Data
@Accessors(chain = true)
public class ApiGetBannerVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("跳转链接")
    private String toLink;

    @ApiModelProperty("图片url")
    private String imgUrl;

    @ApiModelProperty("主题色")
    private String themeColor;

    @ApiModelProperty("排序")
    private Integer sortNum;
}
