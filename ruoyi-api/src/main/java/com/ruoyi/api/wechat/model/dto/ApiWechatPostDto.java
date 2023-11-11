package com.ruoyi.api.wechat.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/15 21:13
 */
@Data
@Accessors(chain = true)
public class ApiWechatPostDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("微信加密签名")
    @NotBlank(message = "微信加密签名不能为空")
    private String signature;

    @ApiModelProperty("时间戳")
    @NotBlank(message = "时间戳不能为空")
    private String timestamp;

    @ApiModelProperty("随机数")
    @NotBlank(message = "随机数不能为空")
    private String nonce;

    @ApiModelProperty("openid")
    @NotBlank(message = "openid不能为空")
    private String openid;

    @ApiModelProperty("加密类型")
    private String encrypt_type;

    @ApiModelProperty("加密签名")
    private String msg_signature;
}
