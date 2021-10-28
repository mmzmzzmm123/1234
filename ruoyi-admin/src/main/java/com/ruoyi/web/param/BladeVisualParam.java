package com.ruoyi.web.param;

import com.ruoyi.system.domain.BladeVisual;
import com.ruoyi.system.domain.BladeVisualConfig;

public class BladeVisualParam {
    private BladeVisualConfig config;

    private BladeVisual visual;

    public BladeVisualParam() {
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
        return "VisualAddParam{" +
                "config=" + config +
                ", visual=" + visual +
                '}';
    }
}
