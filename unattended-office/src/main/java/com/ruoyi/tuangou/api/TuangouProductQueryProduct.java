package com.ruoyi.tuangou.api;

import com.dianping.openapi.sdk.api.base.AbstractAPI;
import com.dianping.openapi.sdk.enums.AuthenticateType;
import com.dianping.openapi.sdk.enums.HttpMethodEnum;
import com.ruoyi.tuangou.entity.TuangouProductQueryProductRequest;
import com.ruoyi.tuangou.entity.TuangouProductQueryProductResponse;

public class TuangouProductQueryProduct extends AbstractAPI<TuangouProductQueryProductResponse> {
    public TuangouProductQueryProduct(TuangouProductQueryProductRequest request){
        this.setAPIParams(request);
    }

    @Override
    public HttpMethodEnum getHttpMethod() {
        return HttpMethodEnum.GET;
    }

    @Override
    public String getHttpUrl() {
        return "https://openapi.dianping.com/router/tuangou/product/queryproduct";
    }

    @Override
    public AuthenticateType getAuthenticateType() {
        return AuthenticateType.SIGN;
    }

    @Override
    public Class getResponseClass() {
        return TuangouProductQueryProductResponse.class;
    }

    @Override
    public Class getRequestClass() {
        return TuangouProductQueryProductRequest.class;
    }

    @Override
    public String getMethod() {
        return "tuangou.product.queryproduct";
    }
}
