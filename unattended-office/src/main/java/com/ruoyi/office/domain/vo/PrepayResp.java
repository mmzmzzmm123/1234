package com.ruoyi.office.domain.vo;

import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import lombok.Data;

@Data
public class PrepayResp {
    WxPayUnifiedOrderV3Result.JsapiResult jsapiResult;
    Long orderId;
}
