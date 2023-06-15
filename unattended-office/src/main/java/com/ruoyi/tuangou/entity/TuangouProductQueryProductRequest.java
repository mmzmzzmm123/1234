package com.ruoyi.tuangou.entity;

import com.dianping.openapi.sdk.api.base.request.BaseSignRequest;
import com.google.common.collect.Maps;
import com.ruoyi.common.config.TuangouConfig;
import lombok.Data;

import java.util.Map;

@Data
public class TuangouProductQueryProductRequest extends BaseSignRequest {
    private String open_shop_uuid;
    private Integer page_no;
    private Integer page_size;

    public TuangouProductQueryProductRequest(TuangouConfig config, String session, String open_shop_uuid){
        super(config.getAppKey(), config.getAppSecret(), session);
        this.open_shop_uuid = open_shop_uuid;
    }

    @Override
    public Map<String, Object> toParams() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("open_shop_uuid", open_shop_uuid);
        if(page_no != null){
            params.put("page_no", page_no);
        }
        if(page_size != null){
            params.put("page_size", page_size);
        }
        return params;
    }
}
