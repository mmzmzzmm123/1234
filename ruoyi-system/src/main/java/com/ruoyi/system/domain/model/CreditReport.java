package com.ruoyi.system.domain.model;

import com.ruoyi.system.domain.BzkZbhZrjgb;
import com.ruoyi.system.domain.ZtkZbhZrjgb;
import com.ruoyi.system.domain.ZtkZhdPfmxb;

/**
 * 信用报告
 */
public class CreditReport {

    /**
     * 个体工商户信贷_标准库_准入监管对象
     */
    private BzkZbhZrjgb bzkZbhZrjgb;
    /**
     * 个体工商户信贷_主题库_准入监管对象
     */
    private ZtkZbhZrjgb ztkZbhZrjgb;
    /**
     * 福建省泉州市个体工商户信贷_主题库_评分模型对象
     */
    private ZtkZhdPfmxb ztkZhdPfmxb;

    public BzkZbhZrjgb getBzkZbhZrjgb() {
        return bzkZbhZrjgb;
    }

    public void setBzkZbhZrjgb(BzkZbhZrjgb bzkZbhZrjgb) {
        this.bzkZbhZrjgb = bzkZbhZrjgb;
    }

    public ZtkZbhZrjgb getZtkZbhZrjgb() {
        return ztkZbhZrjgb;
    }

    public void setZtkZbhZrjgb(ZtkZbhZrjgb ztkZbhZrjgb) {
        this.ztkZbhZrjgb = ztkZbhZrjgb;
    }

    public ZtkZhdPfmxb getZtkZhdPfmxb() {
        return ztkZhdPfmxb;
    }

    public void setZtkZhdPfmxb(ZtkZhdPfmxb ztkZhdPfmxb) {
        this.ztkZhdPfmxb = ztkZhdPfmxb;
    }
}
