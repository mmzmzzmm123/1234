package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.BladeVisual;
import com.ruoyi.system.domain.BladeVisualConfig;

public class BladeVisualVo {
    private BladeVisualConfig config;

    private BladeVisual visual;

    public BladeVisualVo() {
    }

    public BladeVisualConfig getConfig() {
        return config;
    }

    public void setConfig(BladeVisualConfig config) {
        this.config = config;
    }

    public BladeVisual getVisual() {
        return visual;
    }

    public void setVisual(BladeVisual visual) {
        this.visual = visual;
    }

    @Override
    public String toString() {
        return "BladeVisualVo{" +
                "config=" + config +
                ", visual=" + visual +
                '}';
    }
}
