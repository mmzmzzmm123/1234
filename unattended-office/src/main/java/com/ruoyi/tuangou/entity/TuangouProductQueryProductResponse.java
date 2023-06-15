package com.ruoyi.tuangou.entity;

import com.dianping.openapi.sdk.api.base.response.BaseResponse;

public class TuangouProductQueryProductResponse extends BaseResponse {
    private TuangouProductQueryProductResponseEntity data;

    public TuangouProductQueryProductResponseEntity getData() {
        return data;
    }

    public void setData(TuangouProductQueryProductResponseEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
