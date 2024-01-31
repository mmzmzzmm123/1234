package com.ruoyi.system.hglapi.output;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.system.hglapi.HglApiResult;
import lombok.Data;

@Data
public class GetMerchantTokenOutputDTO<T> extends HglApiResult<T> {

    /**
     * 过期时间，单位秒
     */
    @JSONField(name = "Expiress_In")
    private Integer expiressIn;

    /**
     * token类型 Bearer
     */
    @JSONField(name = "Token_Type")
    private String tokenType;


}
