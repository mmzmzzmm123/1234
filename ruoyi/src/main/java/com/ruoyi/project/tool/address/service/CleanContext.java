package com.ruoyi.project.tool.address.service;

import com.ruoyi.project.tool.address.model.CleanAddress;

import java.util.List;

/**
 * 地址清洗
 *
 * @author lihe
 */
public class CleanContext {
    private StringBuilder boundAddress;
    private Boolean multiHao;
    private String address;
    private List<CleanAddress> result;

    public CleanContext(String text) {
        this.boundAddress = new StringBuilder(text);
    }

    public StringBuilder getBoundAddress() {
        return boundAddress;
    }

    public void setBoundAddress(StringBuilder boundAddress) {
        this.boundAddress = boundAddress;
    }

    public Boolean getMultiHao() {
        return multiHao;
    }

    public void setMultiHao(Boolean multiHao) {
        this.multiHao = multiHao;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CleanAddress> getResult() {
        return result;
    }

    public void setResult(List<CleanAddress> result) {
        this.result = result;
    }
}
