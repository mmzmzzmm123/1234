package com.ruoyi.common.weixin.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/16 18:04
 */
@Data
@Accessors(chain = true)
public class WxMsgMiniProgramDto implements Serializable {

    private String title;

    private String appid;
}
