package com.stdiet.custom.domain.wechat;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxAccessToken implements Serializable {
    private String accessToken;

    private Integer expiresIn;
}
