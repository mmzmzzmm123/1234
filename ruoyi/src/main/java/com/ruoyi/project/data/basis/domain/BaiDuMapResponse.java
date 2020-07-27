package com.ruoyi.project.data.basis.domain;

import java.util.List;

/**
 * 百度地图apiResponse
 *
 * @author lihe
 */
public class BaiDuMapResponse {

    private Integer status;
    private List<BaiDuPoint> result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BaiDuPoint> getResult() {
        return result;
    }

    public void setResult(List<BaiDuPoint> result) {
        this.result = result;
    }
}
