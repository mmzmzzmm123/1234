package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class LoginByOtherSourceBody {
    private String code;
    private String source;// wechat
    private String uuid;
}
