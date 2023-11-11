package com.ruoyi.api.wechat.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/15 19:34
 */
@Data
@Accessors(chain = true)
public class ApiWechatAuthGetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "微信加密签名不能为空")
    private String signature;

    @NotBlank(message = "时间戳不能为空")
    private String timestamp;

    @NotBlank(message = "随机数不能为空")
    private String nonce;

    @NotBlank(message = "随机字符串不能为空")
    private String echostr;
}
