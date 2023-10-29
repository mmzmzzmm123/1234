package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class TtlockTokenRes {
    String access_token;
    String refresh_token;
    String uid;
    String openid;
    String scope;
    String token_type;
    String expires_in;
}
