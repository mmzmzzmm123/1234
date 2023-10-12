package com.ruoyi.common.weixin.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author LAM
 * @date 2023/10/10 17:06
 */
@Data
@Accessors(chain = true)
public class WxServiceNotifyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接受用户openId")
    private String toUser;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("跳转页面（默认首页）")
    private String toPage = "pages/index/index";

    @ApiModelProperty("模板对应数据")
    private Map<String,String> data;
}
