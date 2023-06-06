package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class WxPayCallbackResp {
    // 错误码，SUCCESS为清算机构接收成功，其他错误码为失败。 示例值：FAIL
    String code;
    // 返回信息，如非空，为错误原因。
    String message;
}
