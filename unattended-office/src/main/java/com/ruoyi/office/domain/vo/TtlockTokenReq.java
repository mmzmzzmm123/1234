package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class TtlockTokenReq {
    String clientId;
    String clientSecret;
    String username;
    String password;
}
