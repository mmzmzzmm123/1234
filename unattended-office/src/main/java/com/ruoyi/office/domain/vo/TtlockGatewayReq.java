package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class TtlockGatewayReq {
    String clientId;
    String accessToken;
    String lockId;
    String date;
}
