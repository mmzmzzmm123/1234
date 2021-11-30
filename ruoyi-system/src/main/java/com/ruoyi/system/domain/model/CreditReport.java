package com.ruoyi.system.domain.model;

import com.ruoyi.system.domain.BzkZhdPfmxmxb;
import com.ruoyi.system.domain.BzkZhdZrjgmxb;
import com.ruoyi.system.domain.ZtkZhdPfmxjgb;
import com.ruoyi.system.domain.ZtkZhdZrjgjgb;

/**
 * 信用报告
 */
public class CreditReport {

    /**
     * 个体工商户信贷_标准库_评分模型明细对象
     */
    private BzkZhdPfmxmxb bzkZhdPfmxmxb;
    /**
     * 个体工商户信贷_标准库_准入监管明细对象
     */
    private BzkZhdZrjgmxb bzkZhdZrjgmxb;
    /**
     * 个体工商户信贷_主题库_评分模型结果对象
     */
    private ZtkZhdPfmxjgb ztkZhdPfmxjgb;
    /**
     * 个体工商户信贷_主题库_准入监管结果对象
     */
    private ZtkZhdZrjgjgb ztkZhdZrjgjgb;

    public BzkZhdPfmxmxb getBzkZhdPfmxmxb() {
        return bzkZhdPfmxmxb;
    }

    public void setBzkZhdPfmxmxb(BzkZhdPfmxmxb bzkZhdPfmxmxb) {
        this.bzkZhdPfmxmxb = bzkZhdPfmxmxb;
    }

    public BzkZhdZrjgmxb getBzkZhdZrjgmxb() {
        return bzkZhdZrjgmxb;
    }

    public void setBzkZhdZrjgmxb(BzkZhdZrjgmxb bzkZhdZrjgmxb) {
        this.bzkZhdZrjgmxb = bzkZhdZrjgmxb;
    }

    public ZtkZhdPfmxjgb getZtkZhdPfmxjgb() {
        return ztkZhdPfmxjgb;
    }

    public void setZtkZhdPfmxjgb(ZtkZhdPfmxjgb ztkZhdPfmxjgb) {
        this.ztkZhdPfmxjgb = ztkZhdPfmxjgb;
    }

    public ZtkZhdZrjgjgb getZtkZhdZrjgjgb() {
        return ztkZhdZrjgjgb;
    }

    public void setZtkZhdZrjgjgb(ZtkZhdZrjgjgb ztkZhdZrjgjgb) {
        this.ztkZhdZrjgjgb = ztkZhdZrjgjgb;
    }
}
