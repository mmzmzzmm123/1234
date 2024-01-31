package com.ruoyi.system.hglapi.input;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class GetMerchantTokenInputDTO {

    @JSONField(name = "LoginName")
    private String loginName;

    @JSONField(name = "LoginPassword")
    private String loginPassword;

}
